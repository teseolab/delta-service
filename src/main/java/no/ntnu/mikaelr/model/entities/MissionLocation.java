package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mission_locations")
public class MissionLocation {

    private Integer id;
    private String key;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public MissionLocation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_location_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "key")
    public String getKey() {
        return key;
    }

    @Column(name = "latitude", precision = 12, scale = 6, nullable = false)
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", precision = 12, scale = 6,  nullable = false)
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
