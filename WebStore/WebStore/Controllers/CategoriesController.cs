﻿using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebStore.Data;
using WebStore.Data.Entities;
using WebStore.Models.Category;

namespace WebStore.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly AppEFContext _appEFContext;
        private readonly IMapper _mapper;
        public CategoriesController(AppEFContext appEFContext, IMapper mapper)
        {
            _appEFContext = appEFContext;
            _mapper = mapper;
        }
        [HttpGet("list")]
        public async Task<IActionResult> Index()
        {
            var model = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .Select(x => _mapper.Map<CategoryItemViewModel>(x))
                .ToListAsync();

            return Ok(model);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var cat = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .SingleOrDefaultAsync(x => x.Id == id);
            
            if(cat == null)
                return NotFound();

            var model = _mapper.Map<CategoryItemViewModel>(cat);
            return Ok(model);
        }

        [HttpPost("create")]
        public async Task<IActionResult> Create([FromBody] CategoryCreateViewModel model)
        {
            try
            {
                var cat = _mapper.Map<CategoryEntity>(model);
                await _appEFContext.Categories.AddAsync(cat);
                await _appEFContext.SaveChangesAsync();
                return Ok(_mapper.Map<CategoryItemViewModel>(cat));
            }
            catch (Exception ex)
            {
                return BadRequest(new { error=ex.Message});
            }
        }
        [HttpPut("update")]
        public async Task<IActionResult> Put([FromBody] CategoryUpdateViewModel model)
        {
            try
            {
                var cat = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .SingleOrDefaultAsync(x => x.Id == model.Id);

                if (cat == null)
                    return NotFound();

                cat.Name = model.Name;
                cat.Description = model.Description;
                cat.Image = model.Image; 
                await _appEFContext.SaveChangesAsync();

                return Ok(_mapper.Map<CategoryItemViewModel>(cat));
            }
            catch (Exception ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            try
            {
                var cat = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .SingleOrDefaultAsync(x => x.Id == id);

                if (cat == null)
                    return NotFound();

                cat.IsDeleted = true;
                await _appEFContext.SaveChangesAsync();

                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }
    }
}
