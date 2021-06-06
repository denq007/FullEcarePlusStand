package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.basket.Basket;
import com.t_systems.ecare.eCare.basket.BasketImpl;
import com.t_systems.ecare.eCare.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller

public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    PhoneNumberService phoneNumberService;
    @Autowired
    BasketImpl basket;
    @Autowired
    CustomerService customerService;
    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;

    @GetMapping("/contract/createcontract")
    public String createContract(@RequestParam("customerID") int customerID, Model model) {
        ContractDTO contractDTO = new ContractDTO(customerID);
        contractDTO.setNumber(Long.toString(phoneNumberService.createphoneNumber()));
        model.addAttribute("contract", contractDTO);
        contractService.showTariffandOptions(contractDTO);
        return "contract/createContract";
    }

    @PostMapping("/contract/createcontract")
    public String createContract(@ModelAttribute("contract") @Valid ContractDTO contractDTO, Model model, RedirectAttributes attr) {
        Optional<String> error = contractService.create(contractDTO);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            contractService.showTariffandOptions(contractDTO);
            return "contract/createContract";
        }
        attr.addAttribute("id", contractDTO.getCustomerId());
        return "redirect:/customer/showcustomer";
    }

    @GetMapping("/contract/editcontract")
    public String editContract(@RequestParam("id") int id, Model model) {
        ContractDTO dto = contractService.getDto(id);
        if (contractService.isContractBlocked(dto)) {
            model.addAttribute("message", "This contract is blocked");
            return "redirect:/success";
        }
        contractService.showTariffandOptions(dto);
        model.addAttribute("contract", dto);
        return "contract/createContract";
    }

    @PostMapping("/contract/editcontract")
    public String editContract(@ModelAttribute("contract") ContractDTO contractDTO, Model model, RedirectAttributes attr) {
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
    public String showContract(@RequestParam("id") int id, Model model) {
        model.addAttribute("contract", contractService.getDto(id));
        return "contract/showContract";
    }

    @RequestMapping("/contract/showallcontracts")
    public String showAllContracts(Model model) {
        List<ContractDTO> contractDTOList = contractService.showAllContracts();
        model.addAttribute("allContracts", contractDTOList);
        return "contract/allContracts";
    }

    @GetMapping("/contract/editcontract-showbasket")
    public String editContactInBasket(@RequestParam(defaultValue = "0") int id, Model model) {
        if (id == 0) {
            model.addAttribute("message", "The shopping cart is empty now");
            return "contract/editContractInBasket";
        }
        ContractDTO dto = contractService.getDto(id);
        if (contractService.isContractBlocked(dto)) {
            model.addAttribute("message", "This contract is blocked");
            return "redirect:/success";
        }
        contractService.showTariffandOptions(dto);
        if(basket.getTariffName()==null)
        basket = basket.createBasket(dto);
        model.addAttribute("basket", basket);
        return "contract/editContractInBasket";
    }

    @PostMapping("/contract/editcontract-showbasket")
    public String editContract(@ModelAttribute("basket") BasketImpl basket, Model model, RedirectAttributes attr) {
        Set<Integer> optionsId=new HashSet<>();
        optionsId.addAll(this.basket.getOptionsIds());
        optionsId.addAll(basket.getOptionsIds());
        boolean b=tariffService.checkOptionsCompatibility(tariffService.getOptionsById(optionsId.stream().collect(Collectors.toList())));
        if (!b) {
            model.addAttribute("message", "Incompatible options are selected");
            model.addAttribute("basket", this.basket);
            return "contract/editContractInBasket";
        }
        this.basket.getOptionsIds().addAll(optionsId);
        this.basket.setAddNameOptions(optionService.getOptionsNameById(this.basket.getOptionsIds()));
        model.addAttribute("basket", this.basket);
        return "showBasket";
    }

    @RequestMapping("/showbasket")
    public String showBasket(Model model) {
        if (basket.getTariffName()==null||basket==null) {
            model.addAttribute("message", "The shopping cart is empty now");
            return "redirect:/success";
        }
        model.addAttribute("basket", basket);
        return "showBasket";
    }
    @PostMapping("/contract/savecontract")
    public String saveCart(RedirectAttributes attr,HttpSession session)
    {
        ContractDTO dto = contractService.updateByCart(basket);
        attr.addAttribute("id", dto.getCustomerId());
        return "redirect:/customer/showcustomer";
    }


}
