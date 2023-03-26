package com.ECommerce.Emart.Repository;

import com.ECommerce.Emart.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller , Integer>{
}
