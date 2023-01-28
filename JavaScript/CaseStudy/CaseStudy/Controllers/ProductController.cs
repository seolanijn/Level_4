using CaseStudy.DAL;
using CaseStudy.DAL.DAO;
using CaseStudy.DAL.DomainClasses;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace CaseStudy.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController : ControllerBase
    {
        readonly AppDbContext _db;
        public ProductController(AppDbContext context)
        {
            _db = context;
        }
        [HttpGet]
        [Route("{brdid}")]//
        public async Task<ActionResult<List<Product>>> Index(int brdid)
        {
            ProductDAO dao = new(_db);
            List<Product> productsForBrand = await dao.GetAllByCategory(brdid);
            return productsForBrand;
        }
    }
}
