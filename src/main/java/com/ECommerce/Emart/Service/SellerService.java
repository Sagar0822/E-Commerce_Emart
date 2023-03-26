package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Model.Seller;
import com.ECommerce.Emart.Repository.SellerRepository;
import com.ECommerce.Emart.RequestDto.SellerRequestDto;
import com.ECommerce.Emart.Convertor.SellerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public String addSeller(SellerRequestDto sellerRequestDto){
          Seller seller = SellerConvertor.sellerRequestDtoToSeller(sellerRequestDto);
           sellerRepository.save(seller);

          return "Congrats! Now you can sell product!!";
    }
}
