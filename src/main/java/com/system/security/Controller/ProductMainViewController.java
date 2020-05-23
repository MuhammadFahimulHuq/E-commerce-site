package com.system.security.Controller;

import com.system.security.Model.Products;
import com.system.security.Model.Review;
import com.system.security.Model.User;
import com.system.security.Repository.ReviewRepository;
import com.system.security.Service.OrderedService;
import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Date;

@Controller
public class ProductMainViewController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private OrderedService orderedService;



@RequestMapping(value = "/postview/{id}",method = RequestMethod.GET)
public ModelAndView getById(@PathVariable int id,Review review){
    ModelAndView modelAndView = new ModelAndView();


    modelAndView.addObject("product",productsService.findByProductId(id));
    //addtocart
    modelAndView.addObject("products",productsService.getAllProducts());
    modelAndView.addObject("Total",productsService.getTotalCollection().toString());
    modelAndView.addObject("Size",productsService.sizeOfListCollection());
    modelAndView.addObject("addToCartProducts",productsService.getProductsCollection());
    modelAndView.addObject("Specs",productsService.getProductSpecs(id));

    modelAndView.addObject("Review",productsService.getReviewByProductId(id));
modelAndView.addObject("getAverageInteger",productsService.getAverageInteger(id));
    modelAndView.addObject("ReviewSize",productsService.getReviewByProductId(id).size());

    modelAndView.setViewName("postview");

    return modelAndView;
}

@RequestMapping(value = "/comment/{id}",method = RequestMethod.POST)
    public String createComment(@PathVariable int id ,Review review, BindingResult result, Principal principal){
    if(result.hasErrors()){

return "postview";}
    Date d1= new Date();
    review.setDate(d1);
    User user = orderedService.getUserFromPrinciple(principal);
    Products products= productsService.findByProductId(id);
    review.setUser(user);
    review.setProducts(products);
    products.getReviews().add(review);
    user.getReviews().add(review);
    reviewRepository.save(review);

return "redirect:/postview/{id}";

}

}
