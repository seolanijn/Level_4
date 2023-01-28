using CaseStudy.DAL.DomainClasses;
using Microsoft.EntityFrameworkCore;

namespace CaseStudy.DAL.DAO
{
    public class ProductDAO
    {
        private readonly AppDbContext _db;
        public ProductDAO(AppDbContext ctx)
        {
            _db = ctx;
        }
        public async Task<List<Product>> GetAllByCategory(int id)
        {
            return await _db.Products!.Where(item => item.Brand!.Id == id).ToListAsync();
        }
    }
}