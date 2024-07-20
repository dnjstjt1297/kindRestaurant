package org.example.kindrestaurant.service;

import lombok.RequiredArgsConstructor;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.dto.ParkingLotInfo;
import org.example.kindrestaurant.repository.KindRestaurantInfoRepository;
import org.example.kindrestaurant.repository.ParkingLotInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotInfoRepository parkingLotInfoRepository;

    @Transactional
    public void saveAll() {
        parkingLotInfoRepository.saveAll();
    }


    public List<ParkingLotInfo> findParkingLotsByRestaurantId(long restaurantId) throws Exception {
        return parkingLotInfoRepository.findParkingLotsByRestaurantId(restaurantId);
    }


}
