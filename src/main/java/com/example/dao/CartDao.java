package com.example.dao;

import com.example.entity.Cart;
import com.example.model.DeletePolicyFromCartRequest;

import java.util.List;

public interface CartDao {
    public abstract void saveItemToCart(final Cart cart);
    public abstract void deleteItemFromCart(final Integer userId, final Integer policyId);
    public abstract List<Cart> getAllItemsForUser(final Integer userId);
    public abstract void removeAllItemsForUser(final Integer userId);
}
