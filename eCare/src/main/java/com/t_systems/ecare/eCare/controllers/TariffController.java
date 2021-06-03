package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TariffController {
    @Autowired
    TariffService tariffService;

    @GetMapping("/employee/create-tariff")
    public String createTariff(Model model)
    {
        TariffDTO tariffDTO=new TariffDTO();
        tariffService.showAllOptions(tariffDTO);
        model.addAttribute("tariff",tariffDTO);
        return "tariff/createTariff";
    }
    @PostMapping("/employee/create-tariff")
    public String createTariff(@ModelAttribute("tariff")@Valid TariffDTO tariffDTO, Model model, RedirectAttributes attr) {
        Optional<String> error = tariffService.saveTariff(tariffDTO);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute("tariff",tariffDTO);
            return "/tariff/createTariff";
        }
        attr.addAttribute("name", tariffDTO.getName());
        return "redirect:/show-tariff";
    }
    @GetMapping ("/show-tariff")
    public String showTariff(@RequestParam("name")String name,Model model)
    {
        TariffDTO tariffDTO=tariffService.findTariffByName(name);
        if(tariffDTO ==null)
        {
            model.addAttribute("message", "Tariff not found");
          return "employee/employeeCabinet";
         //   return "redirect:/success";
        }
        model.addAttribute("tariff",tariffDTO);
        return "/tariff/showTariff";
    }
    @GetMapping("/employee/delete-tariff")
    public String deleteTariff(@RequestParam("name")String name,Model model)
    {
        Optional<String> error = tariffService.deleteTariff(name);
        if (error.isPresent())
        {
            List<TariffDTO> tariffDTOList=tariffService.showAllTariffs();
            model.addAttribute("allTarifs",tariffDTOList);
            model.addAttribute("message",error.get());
            return "tariff/showAllTariffs";
        }
        return "redirect:/employee/employeecabinet";
    }

    @GetMapping("/employee/edit-tariff")
    public String editTariff(@RequestParam("id")int id,Model model)
    {
        TariffDTO tariffDTO= tariffService.findById(id);
        if(tariffDTO ==null)
        {
            model.addAttribute("message", "Tariff not found");
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute("tariff",tariffDTO);
            return "tariff/editTariff";
        }
        tariffService.showAllOptions(tariffDTO);
        model.addAttribute("tariff",tariffDTO);
        return "/tariff/editTariff";
    }

    @PostMapping("/employee/edit-tariff")
    public String editTariff(@ModelAttribute("tariff") @Valid TariffDTO tariffDTO, BindingResult result, Model model) {
   if(result.hasErrors())
   {
       model.addAttribute("message", "The price was entered incorrectly");
       tariffService.showAllOptions(tariffDTO);
       model.addAttribute("tariff",tariffDTO);
       return "tariff/editTariff";
   }
       Optional<String> error = tariffService.update(tariffDTO);
            if (error.isPresent()) {
            model.addAttribute("message", error.get());
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute("tariff",tariffDTO);
            return "tariff/editTariff";
        }
        model.addAttribute("name", tariffDTO.getName());
        return "redirect:/show-tariff";
    }


    @RequestMapping("show-all-tariffs")
    public String showAllTariffs(Model model)
    {
        List<TariffDTO> tariffDTOList=tariffService.showAllTariffsForEmployee();
        model.addAttribute("allTarifs",tariffDTOList);
        return "tariff/showAllTariffs";
    }


}
