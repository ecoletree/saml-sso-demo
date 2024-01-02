/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : HyungSeok Kim
 * Create Date : 2023. 12. 26.
 * File Name : HomeController.java
 * DESC :
 *****************************************************************/
package me.study.sso.saml.demo.service.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TestController {

    @RequestMapping({"/", ""})
    public ModelAndView index(@AuthenticationPrincipal final Saml2AuthenticatedPrincipal principal, final ModelAndView mav) {
        mav.setViewName(Optional.ofNullable(principal).filter(p -> StringUtils.hasText(p.getName())).isPresent()
                ? "redirect:/secured/hello"
                : "redirect:/public/home");
        return mav;
    }

    @RequestMapping("/public/home")
    public ModelAndView home(@AuthenticationPrincipal final Saml2AuthenticatedPrincipal principal, final ModelAndView mav) {
        mav.setViewName("home");
//        mav.addObject("name", principal.getName());
//        mav.addObject("emailAddress", principal.getFirstAttribute("email"));
//        mav.addObject("userAttributes", principal.getAttributes());
        return mav;
    }

    @RequestMapping("/secured/hello")
    public ModelAndView hello(@AuthenticationPrincipal final Saml2AuthenticatedPrincipal principal, final ModelAndView mav) {
        mav.setViewName("hello");
        mav.addObject("name", principal.getName());
        mav.addObject("emailAddress", principal.getFirstAttribute("email"));
        mav.addObject("userAttributes", principal.getAttributes());
        return mav;
    }

    @RequestMapping("/ecoletree/home")
    public ModelAndView ecoletreeHome(@AuthenticationPrincipal final Saml2AuthenticatedPrincipal principal, final ModelAndView mav) {
        mav.setViewName("redirect:http://www.ecoletree.com");
        return mav;
    }

    @RequestMapping("/ecoletree/hyper-automation")
    public ModelAndView ecoletreeHyperAutomation(@AuthenticationPrincipal final Saml2AuthenticatedPrincipal principal, final ModelAndView mav) {
        mav.setViewName("redirect:http://www.ecoletree.com/hyper-automation");
        return mav;
    }
}