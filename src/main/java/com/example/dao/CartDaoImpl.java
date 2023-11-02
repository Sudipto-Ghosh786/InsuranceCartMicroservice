package com.example.dao;

import com.example.entity.Cart;
import com.example.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveItemToCart(final Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteItemFromCart(final Integer userId, final Integer policyId) {
        cartRepository.deleteByPolicyId(userId, policyId);
    }

    @Override
    public List<Cart> getAllItemsForUser(final Integer userId) {
        return cartRepository.getListOfItemsForUserId(userId);
    }

    @Override
    @Transactional
    public void removeAllItemsForUser(final Integer userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}
