package com.example.handler;

import com.example.dao.CartDao;
import com.example.dao.OrdersDao;
import com.example.entity.Cart;
import com.example.entity.Orders;
import com.example.model.*;
import com.example.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceCartHandlerImpl implements InsuranceCartHandler {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrdersDao ordersDao;

    @Override
    public AddPolicyToCartResponse addPolicyToCart(final AddPolicyToCartRequest addPolicyToCartRequest) {
        try {
			cartDao.saveItemToCart(Cart.builder()
			                .userId(addPolicyToCartRequest.getUserId())
			                .policyId(addPolicyToCartRequest.getPolicyDetails().getPolicyId())
			                .policyDetail(JsonUtils.toJson(addPolicyToCartRequest.getPolicyDetails()))
			        .build());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return AddPolicyToCartResponse.builder()
                .isPolicyAddedSuccessfully(Boolean.TRUE).build();
    }

    @Override
    public DeletePolicyFromCartResponse deletePolicyFromCart(final DeletePolicyFromCartRequest deletePolicyFromCartRequest) {
        cartDao.deleteItemFromCart(deletePolicyFromCartRequest.getUserId(), deletePolicyFromCartRequest.getPolicyId());
        return DeletePolicyFromCartResponse.builder().isPolicyDeleteSuccessfully(true).build();
    }


    @Override
    public CreateOrderFromCartResponse createOrderFromCart(final Integer userId) {
        List<Integer> listOfPolicyId = cartDao.getAllItemsForUser(userId)
        		.stream()
        		.map(Cart::getPolicyId)
        		.collect(Collectors.toList());
        ordersDao.addOrder(Orders.builder()
                .userId(userId)
                .policyId(listOfPolicyId)
                .build());
        cartDao.removeAllItemsForUser(userId);
        return CreateOrderFromCartResponse.builder().isOrderCreated(true).build();
    }
}
