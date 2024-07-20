package org.example.kindrestaurant.service;

import lombok.RequiredArgsConstructor;

import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.repository.KindRestaurantInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KindRestaurantService {

    private final KindRestaurantInfoRepository kindRestaurantInfoRepository;

    @Transactional
    public void saveAll() {
        kindRestaurantInfoRepository.saveAll();
    }


    public List<KindRestaurantInfo> searchRestaurants(String name, String category, Double lat, Double lot) throws Exception {
        return kindRestaurantInfoRepository.searchRestaurants(name, category,lat, lot);
    }

    public KindRestaurantInfo findRestaurants(long id){
        return kindRestaurantInfoRepository.findRestaurants(id);
    }


    @Transactional
    public void insertLoc() throws Exception {
        kindRestaurantInfoRepository.insertLoc();
    }

    public String[] addrToLocation(String addr) throws Exception {
        return KindRestaurantInfoRepository.addrToLocation(addr);
    }


}