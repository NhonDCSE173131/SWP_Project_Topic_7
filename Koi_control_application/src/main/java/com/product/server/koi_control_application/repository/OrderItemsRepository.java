package com.product.server.koi_control_application.repository;

import com.product.server.koi_control_application.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}