package com.example.demo.service;

import com.example.demo.model.UserPrinciple;
import com.example.demo.model.Users;
import com.example.demo.repository.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements UserDetailsService {
    @Autowired
         private Productrepo repo;
    public List<Users> getproduct(){
        return repo.findAll();
    }
//
//    public Product getprodbyid(int prodid) {
//        return repo.findById(prodid).orElse(null);
//    }
//
//    public void addprod(Product prod) {
//        repo.save(prod);
//    }
//    public void updateprod(Product prod) {
//       repo.save(prod);
//    }
//
//    public void deleteprod(int prodid) {
//        repo.deleteById(prodid);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if(user==null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user mot found");
        }
        else
            return new UserPrinciple(user);

    }

    public Users registor(Users users) {
         return repo.save(users);
    }
}
