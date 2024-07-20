package org.example.kindrestaurant.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.example.kindrestaurant.repository.KindRestaurantInfoRepository;

@Entity
@Data
public class KindRestaurantInfo {

    @Id
    private long SH_ID;

    public KindRestaurantInfo() {}
    private String SH_NAME;
    private int INDUTY_CODE_SE;
    private String INDUTY_CODE_SE_NAME;
    private String SH_ADDR;
    private String SH_PHONE;
    private String SH_WAY;

    @Column(length = 1000)
    private String SH_INFO;

    @Column(length = 1000)
    private String SH_PRIDE;
    private String SH_RCMN;
    private String SH_PHOTO;
    private String BASE_YM;

    private Double LAT;
    private Double LOT;


    public KindRestaurantInfo(long SH_ID, String SH_NAME, int INDUTY_CODE_SE, String INDUTY_CODE_SE_NAME,
                              String SH_ADDR, String SH_PHONE, String SH_WAY, String SH_INFO,
                              String SH_PRIDE, String SH_RCMN, String SH_PHOTO, String BASE_YM) throws Exception {
        this.SH_ID = SH_ID;
        this.SH_NAME = SH_NAME;
        this.INDUTY_CODE_SE = INDUTY_CODE_SE;
        this.INDUTY_CODE_SE_NAME = INDUTY_CODE_SE_NAME;
        this.SH_ADDR = SH_ADDR;
        this.SH_PHONE = SH_PHONE;
        this.SH_WAY = SH_WAY;
        this.SH_INFO = SH_INFO;
        this.SH_PRIDE = SH_PRIDE;
        this.SH_RCMN = SH_RCMN;
        this.SH_PHOTO = SH_PHOTO;
        this.BASE_YM = BASE_YM;


    }
    public void initLoc() throws Exception {
        String[] loc = KindRestaurantInfoRepository.addrToLocation(SH_ADDR);

        if(loc[0] != null && loc[1] != null) {
            this.LAT = Double.parseDouble(loc[0]);
            this.LOT = Double.parseDouble(loc[1]);
        }
        else{
            this.LAT = 0.0;
            this.LOT = 0.0;
        }
    }

}