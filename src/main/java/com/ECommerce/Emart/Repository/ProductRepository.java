package com.ECommerce.Emart.Repository;

import com.ECommerce.Emart.Enum.ProductCategory;
import com.ECommerce.Emart.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByProductCategory(ProductCategory productCategory);
}
