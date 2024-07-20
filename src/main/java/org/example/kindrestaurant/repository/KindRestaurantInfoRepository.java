package org.example.kindrestaurant.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.example.kindrestaurant.dto.KindRestaurantInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KindRestaurantInfoRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveAll() {
        try {
            long start = 1;
            long end = 1000;
            while (true) {

                URL url = new URL("http://openAPI.seoul.go.kr:8088/677055674a646e6a333246536c664a/json/ListPriceModelStoreService/" + start + "/" + end + "/");

                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String result = bf.readLine();

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject listPriceModelStoreService = (JSONObject) jsonObject.get("ListPriceModelStoreService");
                if (listPriceModelStoreService == null) {
                    break;
                }
                JSONArray infoArr = (JSONArray) listPriceModelStoreService.get("row");


                for (Object obj : infoArr) {
                    JSONObject tmp = (JSONObject) obj;
                    long id = Long.parseLong((String) tmp.get("SH_ID"));
                    // Check if the data already exists
                    KindRestaurantInfo existingInfo = em.find(KindRestaurantInfo.class, id);


                    if (existingInfo == null) {
                        KindRestaurantInfo infoObj = new KindRestaurantInfo(
                                id,
                                (String) tmp.get("SH_NAME"),
                                Integer.parseInt((String) tmp.get("INDUTY_CODE_SE")),
                                (String) tmp.get("INDUTY_CODE_SE_NAME"),
                                (String) tmp.get("SH_ADDR"),
                                (String) tmp.get("SH_PHONE"),
                                (String) tmp.get("SH_WAY"),
                                (String) tmp.get("SH_INFO"),
                                (String) tmp.get("SH_PRIDE"),
                                ((Double) tmp.get("SH_RCMN")).toString(),
                                (String) tmp.get("SH_PHOTO"),
                                (String) tmp.get("BASE_YM")
                        );

                        em.persist(infoObj);
                        em.flush();
                    }
                }
                start += 1000;
                end += 1000;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertLoc() throws Exception {

        List<KindRestaurantInfo> list = em.createQuery("select k from KindRestaurantInfo k", KindRestaurantInfo.class).getResultList();
        for(KindRestaurantInfo e : list){
            e.initLoc();
        };

        em.flush();
    }

    public List<KindRestaurantInfo> searchRestaurants(String name, String category, Double lat, Double lot) {
        StringBuilder jpql = new StringBuilder("select k from KindRestaurantInfo k where 1=1");

        if(!name.isEmpty())
            jpql.append(" and lower(k.SH_NAME) like :SH_NAME");

        switch (category) {
            case "All":
                jpql.append(" and (k.INDUTY_CODE_SE = 1 or k.INDUTY_CODE_SE = 2 or k.INDUTY_CODE_SE = 3 or k.INDUTY_CODE_SE = 4)");
                break;
            case "한식":
                jpql.append(" and k.INDUTY_CODE_SE = 1");
                break;
            case "중식":
                jpql.append(" and k.INDUTY_CODE_SE = 2");
                break;
            case "일식&경양식":
                jpql.append(" and k.INDUTY_CODE_SE = 3");
                break;
            case "카페&패스트푸드":
                jpql.append(" and k.INDUTY_CODE_SE = 4");
                break;
            default:
                break;
        }


        if(lat!=null && lot!=null){

            jpql.append(" and k.LAT BETWEEN :latMin AND :latMax " +
            "AND k.LOT BETWEEN :lonMin AND :lonMax");
        }

        TypedQuery<KindRestaurantInfo> query = em.createQuery(jpql.toString(), KindRestaurantInfo.class);
        if(!name.isEmpty())
            query.setParameter("SH_NAME", "%" + name.toLowerCase() + "%");


        if(lat!=null &&lot!=null){
            query.setParameter("latMin", lot - 0.02);
            query.setParameter("latMax", lot + 0.02);
            query.setParameter("lonMin", lat - 0.02);
            query.setParameter("lonMax", lat + 0.02);
        }

        return query.getResultList();
    }

    public static String[] addrToLocation(String addr) throws Exception {
        String[] location = new String[2];
        String address = URLEncoder.encode(addr, "UTF-8");

        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        String jsonString = new String();

        String buf;

        URL Url = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) Url.openConnection();
        String auth = "KakaoAK " + "0832119a8a00b2f9c28f1efcef371287";
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Requested-With", "curl");
        conn.setRequestProperty("Authorization", auth);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        while ((buf = br.readLine()) != null) {
            jsonString += buf;
        }
        JSONParser paser = new JSONParser();

        JSONObject J = (JSONObject) paser.parse(jsonString);
        JSONObject meta = (JSONObject) J.get("meta");

        JSONArray data = (JSONArray) J.get("documents");
        long size = (int) meta.get("total_count");

        if (size > 0) {
            JSONObject jsonX = (JSONObject) data.get(0);
            location[0] = (String) jsonX.get("x");
            location[1] = (String) jsonX.get("y");
        }

        return location;
    }

    public KindRestaurantInfo findRestaurants(long id){
        return em.find(KindRestaurantInfo.class,id);
    }

}