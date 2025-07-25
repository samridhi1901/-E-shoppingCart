package com.cg.estore.cartservice.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cg.estore.cartservice.service.ProductFeignClient;

import org.springframework.cloud.openfeign.EnableFeignClients;

import feign.Feign;
import feign.Logger;
import feign.okhttp.OkHttpClient;

@Configuration

public class FeignClientConfig {

//    @Bean
//    public ProductFeignClient productFeignClient() {
//        return Feign.builder()
//                    .client(new OkHttpClient()) // Use the OkHttp client
//                    .target(ProductFeignClient.class, "http://PRODUCT-SERVICE");
//    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
