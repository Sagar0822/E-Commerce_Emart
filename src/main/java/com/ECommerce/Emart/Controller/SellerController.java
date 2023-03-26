package com.ECommerce.Emart.Controller;

import com.ECommerce.Emart.RequestDto.SellerRequestDto;
import com.ECommerce.Emart.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDto sellerRequestDto){
       return sellerService.addSeller(sellerRequestDto);
    }
}
