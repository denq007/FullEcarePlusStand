package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.OptionDTO;
import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.services.OptionService;
import com.t_systems.ecare.eCare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class OptionController {
    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;


    @GetMapping("/employee/create-option")
    public String createOption(Model model)
    {
        OptionDTO option=new OptionDTO();
        model.addAttribute("option",option);
        return "option/createOption";
    }
    @PostMapping("/employee/create-option")
    public String createOption(@ModelAttribute("option") @Valid OptionDTO optionDTO, Model model, RedirectAttributes attr)
    {
        Optional<String> error = optionService.saveOption(optionDTO);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "option/createOption";
        }
        attr.addAttribute("name", optionDTO.getOptionName());
        return "redirect:/show-all-options";
    }
    @RequestMapping("/show-tariff-options")
    public String showOptins(@RequestParam("tariffName") String name,Model model)
    {
        TariffDTO tariffDTO=tariffService.findTariffByName(name);
        model.addAttribute("tariff",tariffDTO);
        return "option/showTariffOptions";
    }

    @RequestMapping("/show-all-options")
    public String showAllOptions(Model model)
    {
        List<OptionDTO> list=optionService.getAllOptions();
        model.addAttribute("allOptions",list);
        return"/option/showAllOptions";
    }
    @GetMapping("employee/editoption")
    public String editClient(@RequestParam("optionId") int id, Model model) {

        model.addAttribute("option",optionService.findById(id));
        return "option/editOption";
    }

    @PostMapping("employee/editoption")
    public String editClient(@ModelAttribute("option") @Valid OptionDTO dto , Model model ) {
        Optional<String> error = optionService.update(dto);
        if (error.isPresent()) {
            model.addAttribute("message", error);
            return "option/createOption";
        }
        return "redirect:/show-all-options";
    }


}
