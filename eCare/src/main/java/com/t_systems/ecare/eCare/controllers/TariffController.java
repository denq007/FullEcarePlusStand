package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TariffController {
    public static final String TARIFF = "tariff";
    public static final String MESSAGE = "message";
    public static final String EDIT_TARIFF = "tariff/editTariff";
    @Autowired
    TariffService tariffService;


    @GetMapping("/employee/create-tariff")
    public String createTariff(Model model)
    {
        TariffDTO tariffDTO=new TariffDTO();
        tariffService.showAllOptions(tariffDTO);
        model.addAttribute(TARIFF,tariffDTO);
        return "tariff/createTariff";
    }

    @PostMapping("/employee/create-tariff")
    public String createTariff(@ModelAttribute(TARIFF)@Valid TariffDTO tariffDTO, Model model, RedirectAttributes attr) {
        Optional<String> error = tariffService.saveTariff(tariffDTO);
        if (error.isPresent()) {
            model.addAttribute(MESSAGE, error.get());
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute(TARIFF,tariffDTO);
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
            model.addAttribute(MESSAGE, "Tariff not found");
            return "redirect:/success";
        }
        model.addAttribute(TARIFF,tariffDTO);
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
            model.addAttribute(MESSAGE,error.get());
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
            model.addAttribute(MESSAGE, "Tariff not found");
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute(TARIFF,tariffDTO);
            return EDIT_TARIFF;
        }
        tariffService.showAllOptions(tariffDTO);
        model.addAttribute(TARIFF,tariffDTO);
        return "/tariff/editTariff";
    }

    @PostMapping("/employee/edit-tariff")
    public String editTariff(@ModelAttribute(TARIFF) @Valid TariffDTO tariffDTO, BindingResult result, Model model) {
   if(result.hasErrors())
   {
       model.addAttribute(MESSAGE, "The price was entered incorrectly");
       tariffService.showAllOptions(tariffDTO);
       model.addAttribute(TARIFF,tariffDTO);
       return EDIT_TARIFF;
   }
       Optional<String> error = tariffService.update(tariffDTO);
            if (error.isPresent()) {
            model.addAttribute(MESSAGE, error.get());
            tariffService.showAllOptions(tariffDTO);
            model.addAttribute(TARIFF,tariffDTO);
            return EDIT_TARIFF;
        }
        model.addAttribute("name", tariffDTO.getName());
        return "redirect:/show-tariff";
    }
    @RequestMapping("/show-all-tariffs")
    public ModelAndView showAllTariffsPagination(@RequestParam(defaultValue = "1") int page)
    {
        ModelAndView model = new ModelAndView();
        List<TariffDTO> tariffDTOList=tariffService.findAll(page);
        int tariffsCount=tariffService.tariffsCount();
        int pagesCount = (tariffsCount + 9)/10;
        model.setViewName("tariff/showAllTariffs");
        model.addObject("allTarifs",tariffDTOList);
        model.addObject("page", page);
        model.addObject("tariffsCount",tariffsCount);
        model.addObject("pagesCount", pagesCount);
        return model;
    }

}
