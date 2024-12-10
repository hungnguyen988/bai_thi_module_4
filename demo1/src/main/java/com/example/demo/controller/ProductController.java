package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import com.example.demo.service.category.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public String search(@RequestParam(required = false, defaultValue = "0") int page,
                         @RequestParam(required = false, defaultValue = "2") int size,
                         @RequestParam(required = false, defaultValue = "") String searchName,
                         Model model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getProductsByName(searchName, pageable);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("searchName", searchName);
        return "list";
    }

    @GetMapping("/search-by-category")
    public String searchByCategory(@RequestParam(required = false, defaultValue = "0") int page,
                         @RequestParam(required = false, defaultValue = "2") int size,
                         @RequestParam(required = false, defaultValue = "") Long categoryId,
                         Model model) {

        Category category = categoryService.getCategoryById(categoryId);
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getProductsByCategory(category, pageable);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("searchCategory", category);
        return "list";
    }




    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addForm";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, @RequestParam("categoryId") Long categoryId,Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        productDTO.setProductCategory(category);
        Product product = new Product();
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addForm";
        }

        BeanUtils.copyProperties(productDTO, product);
        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/delete")
    public String editProductForm(@RequestParam Long id, Model model) {
        productService.deleteProductById(id);
        return "redirect:/product";
    }
}
