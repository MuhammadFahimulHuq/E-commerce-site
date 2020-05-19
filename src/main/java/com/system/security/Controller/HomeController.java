package com.system.security.Controller;

import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private ProductsService productsService;




    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView showHomePost(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("addToCartProducts",productsService.getProductsCollection());
        modelAndView.addObject("products",productsService.getAllProducts());
        modelAndView.addObject("Total",productsService.getTotalCollection().toString());
        modelAndView.addObject("Size",productsService.sizeOfListCollection());
        modelAndView.setViewName("home");
        return modelAndView;
    }



 @RequestMapping(value = "/AddToCart/addProduct/{id}",method = RequestMethod.GET)
    public String createAddToCart(@PathVariable int id){
productsService.addProductCollection(productsService.findByProductId(id));
return "redirect:/";
 }


@RequestMapping(value = "/AddToCart/RemoveProduct/{id}",method = RequestMethod.GET)
    public String deleteAddToCart(@PathVariable int id){
productsService.removeProductCollection(id);
        return "redirect:/";
}

}
