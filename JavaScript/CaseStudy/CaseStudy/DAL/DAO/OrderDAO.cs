using CaseStudy.DAL.DomainClasses;
using CaseStudy.Helpers;
using Microsoft.EntityFrameworkCore;

namespace CaseStudy.DAL.DAO
{
    public class OrderDAO
    {
        private readonly AppDbContext _db;
        public OrderDAO(AppDbContext ctx)
        {
            _db = ctx;
        }
        public async Task<int> AddOrder(int customerid, OrderSelectionHelper[] selections)
        {
            int orderId = -1;
            // we need a transaction as multiple entities involved
            using (var _trans = await _db.Database.BeginTransactionAsync())
            {
                try
                {
                    Order order = new();
                    order.OrderDate = DateTime.Now;
                    order.OrderAmount = 0;
                    order.CustomerId = customerid;
                    // calculate the totals and then add the order row to the table
                    foreach (OrderSelectionHelper selection in selections)
                    {
                        order.OrderAmount += Convert.ToDecimal(selection.product!.MSRP) * selection.Qty;
                    }
                    await _db.Orders!.AddAsync(order);
                    await _db.SaveChangesAsync();
                    // then add each product
                    foreach (OrderSelectionHelper selection in selections)
                    {
                        OrderLineItem olItm = new();
                        olItm.OrderId = order.Id;
                        olItm.ProductId = selection.product!.Id;
                        olItm.SellingPrice = selection.product.MSRP * selection.Qty;

                        if (selection.Qty <= selection.product.QtyOnHand)
                        {
                            selection.product.QtyOnHand -= selection.Qty;
                            olItm.QtySold = selection.Qty;
                            olItm.QtyOrdered = selection.Qty;
                            olItm.QtyBackOrdered = 0;
                        }
                        else
                        {
                            olItm.QtyBackOrdered = selection.Qty - selection.product.QtyOnHand;
                            selection.product.QtyOnBackOrder += selection.Qty - selection.product.QtyOnHand;
                            olItm.QtySold = selection.product.QtyOnHand;
                            selection.product.QtyOnHand = 0;
                            olItm.QtyOrdered = selection.Qty;
                        }

                        var pro = _db.Products!.FirstOrDefault(x => x.Id == selection.product.Id);
                        if (pro != null)
                        {
                            pro.QtyOnHand = selection.product.QtyOnHand;
                            pro.QtyOnBackOrder = selection.product.QtyOnBackOrder;
                        }

                        await _db.OrderLineItems!.AddAsync(olItm);
                        await _db.SaveChangesAsync();
                    }
                    await _trans.CommitAsync();
                    orderId = order.Id;
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                    await _trans.RollbackAsync();
                }
            }
            return orderId;
        }

        public async Task<List<Order>> GetAll(int id)
        {
            return await _db.Orders!.Where(order => order.CustomerId == id).ToListAsync<Order>();
        }

        public async Task<List<OrderDetailsHelper>> GetOrderDetails(int oid, string email)
        {
            Customer? customer = _db.Customers!.FirstOrDefault(customer => customer.Email == email);
            List<OrderDetailsHelper> allDetails = new();
            // LINQ way of doing INNER JOINS
            var results = from o in _db.Orders
                          join oli in _db.OrderLineItems! on o.Id equals oli.OrderId
                          join p in _db.Products! on oli.ProductId equals p.Id
                          where (o.CustomerId == customer!.Id && o.Id == oid)
                          select new OrderDetailsHelper
                          {
                              OrderId = o.Id,
                              ProductId = oli.ProductId,
                              ProductName = p.ProductName,
                              CustomerId = customer!.Id,
                              QtyOrdered = oli.QtyOrdered,
                              QtySold = oli.QtySold,
                              QtyBackOrdered = oli.QtyBackOrdered,
                              OrderDate = o.OrderDate.ToString("yyyy/MM/dd - hh:mm tt"),
                              MSRP = p.MSRP
                          };
            allDetails = await results.ToListAsync();
            return allDetails;
        }
    }
}
