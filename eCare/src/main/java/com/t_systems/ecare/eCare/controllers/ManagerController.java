package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.services.CustomerService;
import com.t_systems.ecare.eCare.services.ManagerService;
import com.t_systems.ecare.eCare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class ManagerController {

    @Autowired
    private ManagerService managerService ;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;


    @RequestMapping("/employeecabinet")
    public String showManagerCabinet(Model model)
    {
        return "employee/employeeCabinet";
    }

    @GetMapping("/showcustomerinformation/{}")
    public String showCustomer( @RequestParam("login") String login,Model model)
    {
        CustomerDTO customerDTO=customerService.getCustomerDTOByEmailUser(login);
        if(customerDTO ==null)
        {
            model.addAttribute("message", "Customer not found");
            return "/tariff/showTariff";
        }
        model.addAttribute("customer",customerDTO);
        return "customer/showCustomer";
    }

    @RequestMapping("/showallcustomer")
    public String showAllCustomer(Model model) {
        List<CustomerDTO> listCustomer= managerService.getAllCustomer();
        model.addAttribute("allCustomer",listCustomer);
        return "employee/all-customer";
    }


    @GetMapping("/addnewcustomer")
    public String addNewCustomer(Model model)
    {
        CustomerDTO customer=new CustomerDTO();
        model.addAttribute("customer",customer);
        return "manager/addnewcustomer";
    }

    @PostMapping(value = "/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDTO)
    {
        managerService.saveCustomer(customerDTO);
        return "redirect:employee/all-customer";
    }

    @GetMapping("/user/block")
    public String blockContract(@RequestParam("id") int id, Model model) {
        int idCustomer=userService.blockByEmployee(id);
        model.addAttribute("id",idCustomer);
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/user/unblock")
    public String unblockContract(@RequestParam("id") int id, Model model) {
        int idCustomer=userService.unblockByEmployee(id);
        model.addAttribute("id",idCustomer);
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/findClientByPhoneNumber")
    public String findByPhoneNumber(@RequestParam(value = "number") String phone,Model model) {
        CustomerDTO customerDTO= customerService.findByPhoneNumber(phone);
        if(customerDTO.getName()==null)
        {
            model.addAttribute("message","this number " +phone+" does not exist");
            return "/employee/employeeCabinet";
        }
        else {
            model.addAttribute("id", customerDTO.getId());
            return "redirect:/customer/showcustomer";
        }
    }


}
