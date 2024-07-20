package org.example.kindrestaurant.controller;

import lombok.AllArgsConstructor;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.service.KindRestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class KindRestaurantController {

    private final KindRestaurantService kindRestaurantService;


    @PostMapping("/search")
    public String search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lot", required = false) Double lot,

            Model model) throws Exception {

        List<KindRestaurantInfo> restaurantInfos = kindRestaurantService.searchRestaurants(name, category, lat, lot);
        model.addAttribute("restaurantInfos", restaurantInfos);

        return "search";
    }



}