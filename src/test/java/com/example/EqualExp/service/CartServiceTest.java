package com.example.EqualExp.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.EqualExp.model.Cart;
import com.example.EqualExp.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CartServiceTest {

  public CartService cartService;
  public ProductService productService;
  private final Map<String, Product> dummyProducts = new HashMap<>();

  @BeforeEach
  public void setup(){
    cartService = new CartService();
    productService = Mockito.mock(ProductService.class);
    dummyProducts.put("cornflakes", getDummyProduct("Corn Flakes",  2.52));
    dummyProducts.put("cheerios", getDummyProduct("Cheerios",  8.43));
    dummyProducts.put("frosties", getDummyProduct("Frosties",  4.99));
    dummyProducts.put("shreddies", getDummyProduct("Shreddies",  4.68));
    dummyProducts.put("weetabix", getDummyProduct("Weetabix",  9.98));
  }

  private Product getDummyProduct(String title, double price){
    return new Product(title, price);
  }

  private Cart updateCartState(Cart cart, Product product){
    cart.getItems().add(product);

    //Calculate subtotal
    double subTotal = 0;
    for (Product pr : cart.getItems()){
      subTotal += pr.getPrice();
    }
    cart.setSubtotal(subTotal);

    //Calculate tax
    double updatedTax = cart.getSubtotal() * (12.5/100);
    cart.setTax(updatedTax);

    //Calculate total payable = subtotal + tax
    double updatedTotal = cart.getSubtotal() + cart.getTax();
    cart.setTotal(updatedTotal);

    return cart;
  }

  @Test
  public void testEmptyCartStatus(){
    Cart expectedCart = new Cart();
    Cart actualCart = cartService.getCart();
    assertThat(actualCart).isEqualTo(expectedCart);
    assertThat(actualCart.getItems()).isEqualTo(new ArrayList<>());
    assertThat(actualCart.getSubtotal()).isEqualTo(0.0);
    assertThat(actualCart.getTax()).isEqualTo(0.0);
    assertThat(actualCart.getTotal()).isEqualTo(0.0);
  }

  @Test
  public void testAddProduct(){
    Cart expectedCart = new Cart();
    updateCartState(expectedCart, dummyProducts.get("cornflakes"));
    Mockito.when(productService.getProductByName("cornflakes")).thenReturn(dummyProducts.get("cornflakes"));
    cartService.productService = productService;
    Cart actualCart = cartService.addProduct("cornflakes");
    assertThat(actualCart).isEqualTo(expectedCart);
    assertThat(actualCart.getItems()).isEqualTo(expectedCart.getItems());
    assertThat(actualCart.getSubtotal()).isEqualTo(expectedCart.getSubtotal());
    assertThat(actualCart.getTax()).isEqualTo(expectedCart.getTax());
    assertThat(actualCart.getTotal()).isEqualTo(expectedCart.getTotal());
  }

  @Test
  public void testAddMultipleProduct(){
    Cart expectedCart = new Cart();
    updateCartState(expectedCart, dummyProducts.get("cornflakes"));
    updateCartState(expectedCart, dummyProducts.get("cheerios"));
    updateCartState(expectedCart, dummyProducts.get("frosties"));
    Mockito.when(productService.getProductByName("cornflakes")).thenReturn(dummyProducts.get("cornflakes"));
    Mockito.when(productService.getProductByName("cheerios")).thenReturn(dummyProducts.get("cheerios"));
    Mockito.when(productService.getProductByName("frosties")).thenReturn(dummyProducts.get("frosties"));
    cartService.productService = productService;
    cartService.addProduct("cornflakes");
    cartService.addProduct("cheerios");
    cartService.addProduct("frosties");
    Cart actualCart = cartService.getCart();
    assertThat(actualCart).isEqualTo(expectedCart);
    assertThat(actualCart.getItems()).isEqualTo(expectedCart.getItems());
    assertThat(actualCart.getSubtotal()).isEqualTo(expectedCart.getSubtotal());
    assertThat(actualCart.getTax()).isEqualTo(expectedCart.getTax());
    assertThat(actualCart.getTotal()).isEqualTo(expectedCart.getTotal());
  }
}
