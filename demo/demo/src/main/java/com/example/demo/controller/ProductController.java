package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.Users;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
   @Autowired
   private ProductService service;
   
   @Autowired
   private AuthenticationService authService;
   
   private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @GetMapping("/products")
    public List<Users> getproduct(){
        return service.getproduct();
    }
//    @GetMapping("/csrf-token")
//    public CsrfToken getcsrftoken(HttpServletRequest request){
//        return (CsrfToken) request.getAttribute("_csrf");
//    }
//    @GetMapping("/products/{prodid}")
//    public Product getprodbyid(@PathVariable int prodid){
//        return service.getprodbyid(prodid);
//    }
//    @PostMapping("/products")
//    public void addprod(@RequestBody Product prod){
//        service.addprod(prod);
//    }
//    @PutMapping("/products")
//    public void updateprod(@RequestBody Product prod) {
//        service.updateprod(prod);
//    }
//    @DeleteMapping("/products/{prodid}")
//    public void deleteprod(@PathVariable int prodid){
//        service.deleteprod(prodid);
//    }
      @PostMapping("/registor")
      public Users registor(@RequestBody Users users){
          users.setPassword(encoder.encode(users.getPassword()));
         return service.registor(users);
     }
     @PostMapping("/login")
     public String login(@RequestBody Users users){
        return authService.verify(users);
     }
}
