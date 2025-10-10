package com.example.ecom_site.service;

import com.example.ecom_site.model.Product;
import com.example.ecom_site.repo.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    Productrepo repo;

    public List<Product> getallproducts() {
        return repo.findAll();
    }

    public Product addproduct(Product prod, MultipartFile imgfile) throws IOException {
        prod.setImgtype(imgfile.getContentType());
        prod.setImgname(imgfile.getOriginalFilename());
        prod.setImgdata(imgfile.getBytes());
        return repo.save(prod);
    }



    public Product getproductbyid(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product prod, MultipartFile imgfile) throws IOException {
        return repo.findById(Math.toIntExact(id)).map(product -> {
            // Update product fields
            product.setName(prod.getName());
            product.setDescription(prod.getDescription());
            product.setBrand(prod.getBrand());
            product.setPrice(prod.getPrice());
            product.setCategory(prod.getCategory());
           // product.setReleaseDate(prod.getReleaseDate());
          //  product.setAvailable(prod.getAvailable());
            product.setQuantity(prod.getQuantity());

            // Update image if provided
            if (imgfile != null && !imgfile.isEmpty()) {
                try {
                    product.setImgtype(imgfile.getContentType());
                    product.setImgname(imgfile.getOriginalFilename());
                    product.setImgdata(imgfile.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process image file", e);
                }
            }

            return repo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteproduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchproduct(String keyword) {
        return repo.searchproduct(keyword);
    }
}
