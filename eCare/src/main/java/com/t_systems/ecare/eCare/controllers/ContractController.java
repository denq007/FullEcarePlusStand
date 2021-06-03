package com.t_systems.ecare.eCare.controllers;
import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.services.ContractService;
import com.t_systems.ecare.eCare.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller

public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    PhoneNumberService phoneNumberService;
 @GetMapping("/contract/createcontract")
 public String createContract(@RequestParam("customerID")int customerID, Model model)
 {
     ContractDTO contractDTO=new ContractDTO(customerID);
     contractDTO.setNumber(Long.toString(phoneNumberService.createphoneNumber()));
     model.addAttribute("contract",contractDTO);
     contractService.showTariffandOptions(contractDTO);
     return "contract/createContract";
 }
    @PostMapping("/contract/createcontract")
    public String createContract(@ModelAttribute("contract") @Valid ContractDTO contractDTO , Model model  , RedirectAttributes attr) {
        Optional<String> error=contractService.create(contractDTO);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            contractService.showTariffandOptions(contractDTO);
            return "contract/createContract";
        }
        attr.addAttribute("id", contractDTO.getCustomerId());
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/contract/editcontract")
    public String editContract(@RequestParam("id") int id, Model model)
    {
        ContractDTO dto = contractService.getDto(id);
        if(contractService.isContractBlocked(dto))
        {
            model.addAttribute("message","This contract is blocked");
            return "redirect:/success";
        }
        contractService.showTariffandOptions(dto);
        model.addAttribute("contract", dto);
        return "contract/createContract";
    }

    @PostMapping("/contract/editcontract")
    public String editContract(@ModelAttribute("contract") ContractDTO contractDTO , Model model, RedirectAttributes attr)
    {
        Optional<String> error = contractService.update(contractDTO);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            contractService.showTariffandOptions(contractDTO);
            model.addAttribute("contract", contractDTO);
            return "contract/createContract";
        }
   //     contractService.update(contractDTO);
        attr.addAttribute("id", contractDTO.getCustomerId());
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/contract/showcontract")
            public String showContract(@RequestParam("id") int id, Model model)
    {
        model.addAttribute("contract", contractService.getDto(id));
        return "contract/showContract";
    }
    @RequestMapping("/contract/showallcontracts")
    public String showAllContracts(Model model)
    {
        List<ContractDTO> contractDTOList= contractService.showAllContracts();
        model.addAttribute("allContracts",contractDTOList);
        return "contract/allContracts";
    }
}
