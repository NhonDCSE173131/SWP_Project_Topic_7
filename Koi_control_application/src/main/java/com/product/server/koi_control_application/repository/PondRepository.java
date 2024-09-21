package com.product.server.koi_control_application.repository;

import com.product.server.koi_control_application.model.Pond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PondRepository extends JpaRepository<Pond, Integer> {
    boolean existsByNameAndUserId(String name, int userId);

    Page<Pond> findAllByUserId(int userId, Pageable pageable);
    boolean existsById(int id);
    boolean existsByIdAndUserId(int id, int userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Pond c WHERE c.name = :name AND c.userId= :userId AND c.id <> :id")
    boolean existsByNameAndUserIdExceptId(@Param("name") String name,
                                           @Param("userId") int userId,
                                           @Param("id") int id);
}
