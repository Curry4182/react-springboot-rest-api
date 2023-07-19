package com.example.gccoffee.service;

import java.util.List;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;

public interface OrderService {
	Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
