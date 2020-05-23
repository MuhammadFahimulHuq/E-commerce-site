package com.system.security.Controller;

import com.system.security.Model.Products;
import com.system.security.Model.ProductsCategory;
import com.system.security.Repository.ProductCategoryRepository;
import com.system.security.Repository.ProductRepository;
import com.system.security.Repository.ProductSpecsRepository;
import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/post")
public class AdminPostController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductSpecsRepository productSpecsRepository;

    @RequestMapping(path ="/createPost" , method = RequestMethod.POST)
    public ModelAndView createProduct(@Valid Products products , BindingResult result, @RequestParam("imageFile") MultipartFile imageFile
                                   ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        if(result.hasErrors()){
    modelAndView.setViewName("adminpost");
    return modelAndView;
}
        byte[] bytes =  imageFile.getBytes();
         products.setProductImage(bytes);
        ProductsCategory category= productsService.getProductId(products.getProductsCategory().getId());
        products.setProductsCategory(category);
        productSpecsRepository.save(products.getProductSpecs());
        productRepository.save(products);
        category.getProducts().add(products);
        productCategoryRepository.save(products.getProductsCategory());


        try {
            productsService.saveImage(imageFile);

        } catch (Exception e) {
            e.printStackTrace();

        }


        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
@GetMapping
    public String ShowForm(Products products, Model model ){
        model.addAttribute("productCategories",productsService.getAllProductCategories());
        return "adminpost";
}

}
