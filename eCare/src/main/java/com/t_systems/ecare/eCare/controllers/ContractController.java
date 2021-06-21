package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.basket.BasketImpl;
import com.t_systems.ecare.eCare.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    private static final String SHOWCUSTOMER="redirect:/customer/showcustomer";
    private static final String CREATECONTRACT="contract/createContract";
    private static final String MESSAGE="message";
    private static final String CONTRACT="contract";
    private static final String CART ="basket";
    private static final String REDIRECT_SUCCESS="redirect:/success";
    private static final String EDIT_CART="contract/editContractInBasket";

    @GetMapping("/contract/createcontract")
    public String createContract(@RequestParam("customerID") int customerID, Model model) {
        ContractDTO contractDTO = new ContractDTO(customerID);
        contractDTO.setNumber(Long.toString(phoneNumberService.createphoneNumber()));
        model.addAttribute(CONTRACT, contractDTO);
        contractService.showTariffandOptions(contractDTO);
        return CREATECONTRACT;
    }

    @PostMapping("/contract/createcontract")
    public String createContract(@ModelAttribute(CONTRACT) @Valid ContractDTO contractDTO, Model model, RedirectAttributes attr) {
        Optional<String> error = contractService.create(contractDTO);
        if (error.isPresent()) {
            model.addAttribute(MESSAGE, error.get());
            contractService.showTariffandOptions(contractDTO);
            return CREATECONTRACT;
        }
        attr.addAttribute("id", contractDTO.getCustomerId());
        return SHOWCUSTOMER;
    }

    @GetMapping("/contract/editcontract")
    public String editContract(@RequestParam("id") int id, Model model) {
        ContractDTO dto = contractService.getDto(id);
        if (contractService.isContractBlocked(dto)) {
            model.addAttribute(MESSAGE, "This contract is blocked");
            return REDIRECT_SUCCESS;
        }
        contractService.showTariffandOptions(dto);
        model.addAttribute(CONTRACT, dto);
        return CREATECONTRACT;
    }

    @PostMapping("/contract/editcontract")
    public String editContract(@ModelAttribute(CONTRACT) ContractDTO contractDTO, Model model, RedirectAttributes attr) {
        Optional<String> error = contractService.update(contractDTO);
        if (error.isPresent()) {
            model.addAttribute(MESSAGE, error.get());
            contractService.showTariffandOptions(contractDTO);
            model.addAttribute(CONTRACT, contractDTO);
            return CREATECONTRACT;
        }
        attr.addAttribute("id", contractDTO.getCustomerId());
        return SHOWCUSTOMER;
    }

    @GetMapping("/contract/showcontract")
    public String showContract(@RequestParam("id") int id, Model model) {
        model.addAttribute(CONTRACT, contractService.getDto(id));
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
            model.addAttribute(MESSAGE, "The shopping cart is empty now");
            return EDIT_CART;
        }
        ContractDTO dto = contractService.getDto(id);
        if (contractService.isContractBlocked(dto)) {
            model.addAttribute(MESSAGE, "This contract is blocked");
            return REDIRECT_SUCCESS;
        }
        contractService.showTariffandOptions(dto);
        if (basket.getTariffName() == null) {
            basket = basket.createBasket(dto);
        }
        model.addAttribute(CART, basket);
        return EDIT_CART;
    }

    @PostMapping("/contract/editcontract-showbasket")
    public String editContract(@ModelAttribute("basket") BasketImpl basket, Model model, RedirectAttributes attr) {
        Set<Integer> optionsId = new HashSet<>();
        optionsId.addAll(this.basket.getOptionsIds());
        optionsId.addAll(basket.getOptionsIds());
        boolean b = tariffService.checkOptionsCompatibility(tariffService.getOptionsById(optionsId.stream().collect(Collectors.toList())));
        if (!b) {
            model.addAttribute(MESSAGE, "Incompatible options are selected");
            model.addAttribute(CART, this.basket);
            return EDIT_CART;
        }
        this.basket.getOptionsIds().addAll(optionsId);
        this.basket.setAddNameOptions(optionService.getOptionsNameById(this.basket.getOptionsIds()));
        model.addAttribute(CART, this.basket);
        return "showBasket";
    }

    @RequestMapping("/showbasket")
    public String showBasket(Model model) {
        if (basket.getTariffName() == null) {
            model.addAttribute(MESSAGE, "The shopping cart is empty now");
            return REDIRECT_SUCCESS;
        }
        model.addAttribute(CART, basket);
        return "showBasket";
    }

    @PostMapping("/contract/savecontract")
    public String saveCart(RedirectAttributes attr, HttpSession session) {
        ContractDTO dto = contractService.updateByCart(basket);
        attr.addAttribute("id", dto.getCustomerId());
        return SHOWCUSTOMER;
    }


}
