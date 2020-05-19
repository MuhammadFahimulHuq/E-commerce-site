package com.system.security.Controller;

import com.system.security.Service.OrderedService;
import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller

public class ProfileController {

    @Autowired
    private OrderedService orderedService;
    @Autowired
    private ProductsService productsService;


    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String getProfile(Model model, Principal principal){

        model.addAttribute("orders",orderedService.orderListCollection(principal));
        model.addAttribute("ordersCart",orderedService.fetchProduct(principal));

        model.addAttribute("Total",orderedService.TotalProducts(principal));
        model.addAttribute("Size",orderedService.fetchProduct(principal).size());
        return "profile";
    }

}
