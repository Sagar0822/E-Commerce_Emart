package com.ECommerce.Emart.Controller;

import com.ECommerce.Emart.RequestDto.OrderRequestDto;
import com.ECommerce.Emart.ResponseDTO.OrderResponseDto;
import com.ECommerce.Emart.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public String addCart(@RequestBody OrderRequestDto orderRequestDto){
        String response = "";
        try{
            response = cartService.addCart(orderRequestDto);
        }
        catch(Exception e){
            return e.getMessage();
        }
        return response;
    }

    @PostMapping("/checkout")
    public ResponseEntity checkOut(@RequestParam("customerId") int customerId){

        List<OrderResponseDto> orderResponseDtoList;
        try{
            orderResponseDtoList = cartService.checkOut(customerId);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(orderResponseDtoList,HttpStatus.ACCEPTED);
    }

}
