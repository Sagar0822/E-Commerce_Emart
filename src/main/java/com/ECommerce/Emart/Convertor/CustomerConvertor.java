package com.ECommerce.Emart.Convertor;

import com.ECommerce.Emart.Model.Customer;
import com.ECommerce.Emart.RequestDto.CustomerRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConvertor {

    public static Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .mobNo(customerRequestDto.getMobNo())
                .age(customerRequestDto.getAge())
                .build();
    }
}
