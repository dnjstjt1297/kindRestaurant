package org.example.kindrestaurant.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ParkingLotInfo {
    @Id @GeneratedValue
    private long id;
    public ParkingLotInfo() {}

    private long PKLT_CD;
    private String PKLT_NM;
	private String ADDR;
	private String CHGD_FREE_SE;
	private Double LAT;
	private Double LOT;

    public ParkingLotInfo(long PKLT_CD, String PKLT_NM,String ADDR, String CHGD_FREE_SE, String LAT, String LOT){
        this.PKLT_CD = PKLT_CD;
        this.PKLT_NM = PKLT_NM;
        this.ADDR = ADDR;
        this.CHGD_FREE_SE = CHGD_FREE_SE;
        this.LAT = Double.parseDouble(LAT);
        this.LOT = Double.parseDouble(LOT);
    }

}
