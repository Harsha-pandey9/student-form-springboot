package com.example.ecom_site.controller;

import com.example.ecom_site.model.Product;
import com.example.ecom_site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getallproduct() {

        return new ResponseEntity<>(service.getallproducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproductbyid(@PathVariable int id) {
        Product product = service.getproductbyid(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addproduct(@RequestPart Product prod,
                                        @RequestPart MultipartFile imgfile) {
        try {
            Product product = service.addproduct(prod, imgfile);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getprodimgbyid(@PathVariable("id") int prodid) {
        Product product = service.getproductbyid(prodid);
        byte[] imgfile = product.getImgdata();

        if (imgfile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImgtype()))
                .body(imgfile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestPart Product prod,
            @RequestPart(required = false) MultipartFile imgfile) {
        try {
            Product product = service.updateProduct(id, prod, imgfile);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String>deleteproduct(@PathVariable int id){
        Product prod =service.getproductbyid(id);
        if(prod!=null) {
            service.deleteproduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not Found",HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>>searchproduct(@RequestParam String keyword){
        List<Product>products=service.searchproduct(keyword);
       return new ResponseEntity<>(products,HttpStatus.OK);
    }
}