package com.api.repository;

import com.api.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Component
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByEmailId(String emailId);

    @Query("SELECT c.name FROM Product c WHERE c.emailId = :emailId")
    List<String> findNameByEmailId(@Param("emailId") String emailId);

    @Query("UPDATE Product c SET c.emailId = ?1 WHERE c.productId = ?2")
    @Modifying
    @Transactional
    Integer updateProductEmailId(String updateProductByEmailId, Integer productId);
}