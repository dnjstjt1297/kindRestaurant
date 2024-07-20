package org.example.kindrestaurant.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.example.kindrestaurant.dto.ParkingLotInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ParkingLotInfoRepository {

    @Autowired
    private final EntityManager em;


    public void saveAll() {
        try {
            long start = 1;
            long end = 1000;
            while (true) {
                URL url = new URL("http://openAPI.seoul.go.kr:8088/677055674a646e6a333246536c664a/json/GetParkInfo/" + start + "/" + end + "/");

                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String result = bf.readLine();


                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject getParkInfoService = (JSONObject) jsonObject.get("GetParkInfo");
                if (getParkInfoService == null) {
                    break;
                }
                JSONArray infoArr = (JSONArray) getParkInfoService.get("row");

                for (Object obj : infoArr) {
                    JSONObject tmp = (JSONObject) obj;

                    long PKLT_CD = Long.parseLong((String) tmp.get("PKLT_CD"));

                    ParkingLotInfo existingInfo = em.find(ParkingLotInfo.class, PKLT_CD);

                    if (existingInfo == null) {
                        ParkingLotInfo infoObj = new ParkingLotInfo(
                                PKLT_CD,
                                (String) tmp.get("PKLT_NM"),
                                (String) tmp.get("ADDR"),
                                (String) tmp.get("CHGD_FREE_SE"),
                                (String) tmp.get("LAT").toString(),
                                (String) tmp.get("LOT").toString()
                        );

                        em.persist(infoObj);
                    }
                }
                start += 1000;
                end += 1000;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ParkingLotInfo> findParkingLotsByRestaurantId(long restaurantId) throws Exception {
        KindRestaurantInfo k = em.find(KindRestaurantInfo.class, restaurantId);

        String[] location = KindRestaurantInfoRepository.addrToLocation(k.getSH_ADDR());


        Double longitude = 0.0;
        Double latitude = 0.0;

        if(location[0] !=null && location[1]!=null){
            longitude = Double.parseDouble(location[0]);
            latitude = Double.parseDouble(location[1]);
        }

        // 숫자 범위를 문자열로 처리해야 함
        String queryStr = "SELECT p FROM ParkingLotInfo p WHERE " +
                "p.LAT BETWEEN :latMin AND :latMax " +
                "AND p.LOT BETWEEN :lonMin AND :lonMax";

        TypedQuery<ParkingLotInfo> query = em.createQuery(queryStr, ParkingLotInfo.class);
        query.setParameter("latMin", latitude - 0.007);
        query.setParameter("latMax", latitude + 0.007);
        query.setParameter("lonMin", longitude - 0.007);
        query.setParameter("lonMax", longitude + 0.007);

        List<ParkingLotInfo> results = query.getResultList();

        return removeDuplicateParkingLots(results);
    }

    private List<ParkingLotInfo> removeDuplicateParkingLots(List<ParkingLotInfo> parkingLots) {
        Map<String, ParkingLotInfo> uniqueParkingLots = new LinkedHashMap<>();
        for (ParkingLotInfo parkingLot : parkingLots) {
            uniqueParkingLots.put(parkingLot.getPKLT_NM(), parkingLot);
        }
        return new ArrayList<>(uniqueParkingLots.values());
    }


}
