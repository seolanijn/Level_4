using CaseStudy.DAL;
using CaseStudy.DAL.DAO;
using CaseStudy.DAL.DomainClasses;
using Microsoft.AspNetCore.Mvc;
using CaseStudy.Helpers;
using Microsoft.AspNetCore.Authorization;
namespace CaseStudy.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class OrderController : ControllerBase
    {
        readonly AppDbContext _ctx;
        public OrderController(AppDbContext context) // injected here
        {
            _ctx = context;
        }
        [HttpPost]
        [Produces("application/json")]
        public async Task<ActionResult<string>> Index(OrderHelper helper)
        {
            string retVal;
            try
            {
                CustomerDAO cDao = new(_ctx);
                Customer? orderOwner = await cDao.GetByEmail(helper.Email);
                OrderDAO oDao = new(_ctx);
                int orderId = await oDao.AddOrder(orderOwner!.Id, helper.Selections!);
                if (orderId > 0)
                {
                    bool boFlag = false;
                    foreach (OrderSelectionHelper selection in helper.Selections!)
                    {
                        if (selection.Qty > selection.product!.QtyOnHand)
                            boFlag = true;
                    }
                    if (boFlag)
                        retVal = "Order " + orderId + " Created! Goods backordered!";
                    else
                        retVal = "Order " + orderId + " Created!";
                }
                else
                    retVal = "Order " + orderId + "is not created";
            }
            catch (Exception ex)
            {
                retVal = "Order not created " + ex.Message;
            }
            return retVal;
        }

        [Route("{email}")]
        [HttpGet]
        public async Task<ActionResult<List<Order>>> List(string email)
        {
            List<Order> trays; ;
            CustomerDAO cDao = new(_ctx);
            Customer? orderOwner = await cDao.GetByEmail(email);
            OrderDAO oDao = new(_ctx);
            trays = await oDao.GetAll(orderOwner!.Id);
            return trays;
        }

        [Route("{orderid}/{email}")]
        [HttpGet]
        public async Task<ActionResult<List<OrderDetailsHelper>>> GetOrderDetails(int orderid, string email)
        {
            OrderDAO dao = new(_ctx);
            return await dao.GetOrderDetails(orderid, email);
        }

    }
}