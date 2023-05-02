package com.example.EqualExp.service;

import com.example.EqualExp.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

  private static final String PRODUCT_API = "https://equalexperts.github.io/backend-take-home-test-data/";
  private static final String PRODUCT_TYPE = ".json";

  public Product getProductByName(String productName){
    Product product = null;
    try{
      RestTemplate restTemplate = new RestTemplate();
      product = restTemplate.getForObject(PRODUCT_API.concat(productName)
          .concat(PRODUCT_TYPE), Product.class);
    }catch (RestClientException ex){
      ex.printStackTrace();
    }
    return product;
  }

}
