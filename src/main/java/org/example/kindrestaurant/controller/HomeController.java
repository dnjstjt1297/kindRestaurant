package org.example.kindrestaurant.controller;

import lombok.RequiredArgsConstructor;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.service.KindRestaurantService;
import org.example.kindrestaurant.service.ParkingLotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final KindRestaurantService kindRestaurantService;
    private final ParkingLotService parkingLotService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) Double lat, @RequestParam(required = false) Double lot, Model model) {
        model.addAttribute("lat", lat);
        model.addAttribute("lot", lot);
        return "home";
    }


    @PostMapping("/update")
    public ModelAndView update() {
        try {
            kindRestaurantService.saveAll();
            kindRestaurantService.insertLoc();
            parkingLotService.saveAll();
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("errorMessage", "Error update: " + e.getMessage());

            return mav;
        }
    }

    @GetMapping("/resetLocation")
    public ModelAndView resetLocation() {
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("lat", null);
        mav.addObject("lot", null);
        return mav;
    }
}

