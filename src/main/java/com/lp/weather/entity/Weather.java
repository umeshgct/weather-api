package com.lp.weather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity class for persisit value in databse
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 50)
    @Column(name = "city", unique = true)
    private String city;

    @Size(max = 50)
    @Column(name = "country")
    private String country;

    @Column(name = "temperature")
    private Double temperature;
}
