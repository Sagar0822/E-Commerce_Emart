package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Convertor.CustomerConvertor;
import com.ECommerce.Emart.Model.Cart;
import com.ECommerce.Emart.Model.Customer;
import com.ECommerce.Emart.Repository.CustomerRepository;
import com.ECommerce.Emart.RequestDto.CustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public String addCustomer(CustomerRequestDto customerRequestDto){

        Customer customer = CustomerConvertor.CustomerRequestDtoToCustomer(customerRequestDto);

        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        customer.setCart(cart);

        customerRepository.save(customer);

        return "WellCome To Emart";
    }
}
