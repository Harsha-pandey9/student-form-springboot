package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;
@Component
@Entity
public class Product {
    @Id
    private Integer productid;
    private String productname;
    private Double price;

    public Product() {}

    public Product(Integer productid, Double price, String productname) {

        this.productid = productid;
        this.price = price;
        this.productname = productname;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }
}
