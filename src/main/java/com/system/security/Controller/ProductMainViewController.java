package com.system.security.Controller;

import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductMainViewController {
    @Autowired
    private ProductsService productsService;




@RequestMapping(value = "/postview/{id}",method = RequestMethod.GET)
public ModelAndView getById(@PathVariable int id){
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("product",productsService.findByProductId(id));
    //addtocart
    modelAndView.addObject("products",productsService.getAllProducts());
    modelAndView.addObject("Total",productsService.getTotalCollection().toString());
    modelAndView.addObject("Size",productsService.sizeOfListCollection());
    modelAndView.addObject("addToCartProducts",productsService.getProductsCollection());
    modelAndView.setViewName("postview");


            return modelAndView;
}


}
