package com.cg.estore.orderservice.serviceImpl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cg.estore.orderservice.entity.User;



@FeignClient( url = "http://localhost:8085/profiles",name = "user-service")  
public interface UserFeignClient {

    @GetMapping("/getfullName/{fullName}") // This line is changed
    User getProfileByFullName(@PathVariable("fullName") String fullName);

    // Other methods related to User Service can be added here
    
    @GetMapping("/getUser/{profileId}")
    public ResponseEntity<User> getByProfileId(@PathVariable String profileId);
}
