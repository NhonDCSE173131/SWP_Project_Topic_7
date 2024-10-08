package com.product.server.koi_control_application.service_interface;

import com.product.server.koi_control_application.pojo.request.CartDTO;
import com.product.server.koi_control_application.model.Cart;
import com.product.server.koi_control_application.pojo.response.CartProductDTO;

import java.util.List;

public interface ICartService {
    Cart createCart(Cart cart,int validUserId) throws IllegalAccessException;
    Cart updateCart(CartDTO cartDTO,int userId);
    void deleteCart(int productId, int userId);

    List<CartProductDTO> getCart(int userId);

    void clearCart(int userId);
}