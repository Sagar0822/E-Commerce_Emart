package com.ECommerce.Emart.Repository;

import com.ECommerce.Emart.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository <Cart, Integer>{
}
