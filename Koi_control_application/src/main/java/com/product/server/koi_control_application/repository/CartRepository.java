package com.product.server.koi_control_application.repository;

import com.product.server.koi_control_application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RepositoryRestResource(exported = false)
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(int userId);
    void deleteByUserIdAndProductId(int userId, int productId);

    @Query("select c from Cart c where c.productId = ?1 and c.userId = ?2")
    Optional<Cart> findByProductIdAndUserId(int productId, int userId);


    @Modifying
    @Query("delete from Cart c where c.productId = ?1")
    Integer deleteByProductId(int productId);

    @Query("select c from Cart c where c.productId = ?1")
    Integer findByProductId(int productId);

    @Modifying
    @Query("delete from Cart c where c.userId = ?1")
    Integer deleteByUserId(int userId);
}
