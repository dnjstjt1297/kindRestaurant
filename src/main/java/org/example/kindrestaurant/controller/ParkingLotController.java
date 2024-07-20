package org.example.kindrestaurant.controller;


import lombok.AllArgsConstructor;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.dto.ParkingLotInfo;
import org.example.kindrestaurant.service.KindRestaurantService;
import org.example.kindrestaurant.service.ParkingLotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class ParkingLotController {

    private final KindRestaurantService kindRestaurantService;
    private final ParkingLotService parkingLotService;

    @GetMapping("/restaurant/{id}")
    public String getRestaurantDetails(@PathVariable("id") long restaurantId, Model model) throws Exception {

        KindRestaurantInfo restaurantInfo = kindRestaurantService.findRestaurants(restaurantId);
        List<ParkingLotInfo> parkingLots = parkingLotService.findParkingLotsByRestaurantId(restaurantId);

        model.addAttribute("restaurantInfo", restaurantInfo);
        model.addAttribute("parkingLots", parkingLots);

        return "restaurant";
    }
}
