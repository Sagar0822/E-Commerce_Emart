package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Convertor.CardConvertor;
import com.ECommerce.Emart.Model.Card;
import com.ECommerce.Emart.Model.Customer;
import com.ECommerce.Emart.Repository.CardRepository;
import com.ECommerce.Emart.Repository.CustomerRepository;
import com.ECommerce.Emart.RequestDto.CardRequestDto;
import com.ECommerce.Emart.ResponseDTO.CardDto;
import com.ECommerce.Emart.ResponseDTO.CardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ECommerce.Emart.Exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CustomerRepository customerRepository;
     public CardResponseDto addCard(CardRequestDto cardRequestDto)throws CustomerNotFoundException{
         Customer customer;
         try {
             customer = customerRepository.findById(cardRequestDto.getCustomerId()).get();
         }
         catch(Exception e){
             throw new CustomerNotFoundException("Invalid Customer Id");
         }
         //Set data Dto to card
         Card card = CardConvertor.cardRequestDtoToCard(cardRequestDto);
         card.setCustomer(customer);

         customer.getCards().add(card);

         customerRepository.save(customer);

         //
         CardResponseDto cardResponseDto = new CardResponseDto();
         cardResponseDto.setCustomerName(customer.getName());

         List<CardDto> cardDtoList = new ArrayList<>();

         CardDto cardDto = new CardDto();
         for(Card card1 : customer.getCards()) {
             cardDto.setCardNo(card1.getCardNo());
             cardDto.setCardType(card1.getCardType());

             cardDtoList.add(cardDto);
         }
         cardResponseDto.setCardDtos(cardDtoList);

         return cardResponseDto;
     }
}
