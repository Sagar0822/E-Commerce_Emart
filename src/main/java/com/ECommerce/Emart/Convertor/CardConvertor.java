package com.ECommerce.Emart.Convertor;

import com.ECommerce.Emart.Model.Card;
import com.ECommerce.Emart.Model.Customer;
import com.ECommerce.Emart.RequestDto.CardRequestDto;

public class CardConvertor {

    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto){
        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .build();
    }
}
