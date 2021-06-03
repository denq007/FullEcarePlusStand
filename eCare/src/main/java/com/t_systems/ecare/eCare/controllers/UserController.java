package com.t_systems.ecare.eCare.controllers;

import com.t_systems.ecare.eCare.DTO.UserDTO;
import com.t_systems.ecare.eCare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.io.IOException;
import java.util.Optional;


@Controller
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/registration")
    public String registrationUser(Model model)
    {
        UserDTO user=new UserDTO();
        model.addAttribute("user",user);
        return "registrationUser";
    }
    @PostMapping("/user/saveuser")
    public String addUser(@ModelAttribute("user") @Valid UserDTO user, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes attr)
    {
        if (result.hasErrors()) {
            UserDTO newUser=new UserDTO();
            model.addAttribute("user",newUser);
            return "registrationUser";
        }
        Optional<String> error=userService.saveUser(user);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "login";
        }
        attr.addAttribute("login",user.getUserLogin());
        return "redirect:/employee/showcustomerinformation/{}";
    }

    @GetMapping("/login")
    public ModelAndView signIn(String error)
    {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("message", "Wrong login or password");
        }
        return model;
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        if(role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/indexAdmin"));
        }
        else if(role.contains("ROLE_CUSTOMER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/customer/showcustomerinformation"));
        }
        else if(role.contains("ROLE_EMPLOYEE")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/employee/employeecabinet"));
        }

    }
/*    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logoutDo(HttpServletRequest request,HttpServletResponse response){
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "home";
    }*/
/*    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }*/
}
