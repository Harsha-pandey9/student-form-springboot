package com.example.ecom_site.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date relesedate;
    private boolean avilable;
    private int quantity;
    private String imgtype;
    private String imgname;
    @Lob
    private byte[] imgdata;


}
