package com.hexalyte.sf_serviceprovider_application.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "locations")

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationId")
    private Integer locationID;


    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "Address", nullable = false, length = 255)
    private String address;

    @Column(name = "City", length = 100)
    private String city;

    @Column(name = "Country", length = 100)
    private String country;

    @Column(name = "Latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "Longitude", precision = 11, scale = 8)
    private BigDecimal longitude;





















}
