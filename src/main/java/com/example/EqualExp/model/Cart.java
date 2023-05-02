package com.example.EqualExp.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
  private List<Product> items = new ArrayList<>();
  private double subtotal;
  private double tax;
  private double total;
}
