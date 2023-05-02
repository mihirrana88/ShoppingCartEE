package com.example.EqualExp.controller;

import com.example.EqualExp.model.Cart;
import com.example.EqualExp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

  @Autowired
  private CartService cartService;

  @GetMapping("/add-to-cart/{product-name}")
  public ResponseEntity<?> getShoppingCart(@PathVariable("product-name") String productName) {
    Cart cart = cartService.addProduct(productName);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @GetMapping("/cart")
  public ResponseEntity<?> getCart() {
    Cart cart =  cartService.getCart();
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

}
