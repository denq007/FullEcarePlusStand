package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.services.CustomerService;
import com.t_systems.ecare.eCare.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
@Slf4j
@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    public static final String CUSTOMER = "customer";
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @GetMapping("/showcustomerinformation")
    public String showCustomer(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerDTO customerDTO=customerService.getCustomerDTOByEmailUser(auth.getName());
         model.addAttribute(CUSTOMER,customerDTO);
        return "customer/showCustomer";
    }
    @GetMapping("/showcustomer")
    public String showCustomer( @RequestParam("id") int id,Model model)
    {
        CustomerDTO customerDTO=customerService.findById(id);
        if(customerDTO ==null)
        {
            model.addAttribute("message", "Customer not found");
            return "employee/employeeCabinet";
        }
        model.addAttribute(CUSTOMER,customerDTO);
        return "customer/showCustomer";
    }


    @GetMapping("/editcustomer")
    public String editClient(@RequestParam("customerID") int id, Model model) {

        model.addAttribute(CUSTOMER,customerService.findById(id));
        return "customer/createCustomer";
    }

    @PostMapping("/editcustomer")
    public String editClient(@ModelAttribute(CUSTOMER)@Valid CustomerDTO customerDTO , Model model ) {
        Optional<String> error = customerService.update(customerDTO);
        if (error.isPresent()) {
            model.addAttribute(CUSTOMER, customerDTO);
            return "customer/createCustomer";
        }
        model.addAttribute("id",customerDTO.getId());
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/user/block")
    public String blockContract(@RequestParam("id") int id, Model model) {
        userService.blockByCustomer(id);
        return "redirect:/customer/showcustomerinformation";
    }

    @GetMapping("/user/unblock")
    public String unblockContract(@RequestParam("id") int id, Model model) {
        userService.unblockByCustomer(id);
        return "redirect:/customer/showcustomerinformation";
    }
}
