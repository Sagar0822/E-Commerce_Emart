package com.ECommerce.Emart.Convertor;

import com.ECommerce.Emart.Model.Seller;
import com.ECommerce.Emart.RequestDto.SellerRequestDto;

public class SellerConvertor {

    public static Seller sellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){
        return Seller.builder()
                .name(sellerRequestDto.getName())
                .email(sellerRequestDto.getEmail())
                .mobNo(sellerRequestDto.getMobNo())
                .panNo(sellerRequestDto.getPanNo())
                .build();
    }
}
