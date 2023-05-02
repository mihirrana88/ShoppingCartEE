package com.example.EqualExp.service;

import com.example.EqualExp.model.Cart;
import com.example.EqualExp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  @Autowired
  ProductService productService;

  private Cart cart = new Cart();

  public Cart getCart(){
    return this.cart;
  }

  public Cart addProduct(String productName){
    Product product = productService.getProductByName(productName);
    if(null != product){
      calculateCartState(product);
    }
    return this.cart;
  }

  private void calculateCartState(Product product){
    this.cart.getItems().add(product);
    calculateCartSubtotal();
    calculateCartTax();
    calculateCartTotal();
  }

  private void calculateCartSubtotal(){
    double subTotal = 0;
    for (Product product : cart.getItems()){
      subTotal += product.getPrice();
    }
    this.cart.setSubtotal(subTotal);
  }

  private void calculateCartTax(){
    double updatedTax = this.cart.getSubtotal() * (12.5/100);
    this.cart.setTax(updatedTax);
  }

  private void calculateCartTotal(){
    double updatedTotal = this.cart.getSubtotal() + this.cart.getTax();
    this.cart.setTotal(updatedTotal);
    // I haven't seen rounded up value in github example hence commenting out
    //this.cart.setTotal(Math.round(updatedTotal));
  }

}
