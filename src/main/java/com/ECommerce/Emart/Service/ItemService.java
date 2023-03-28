package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Exception.ProductNotFoundException;
import com.ECommerce.Emart.Model.Item;
import com.ECommerce.Emart.Model.Product;
import com.ECommerce.Emart.Repository.ItemRepository;
import com.ECommerce.Emart.Repository.ProductRepository;
import com.ECommerce.Emart.ResponseDTO.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ProductRepository productRepository;

    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException{
        Product product;

        try{
            product = productRepository.findById(productId).get();
        }
        catch(Exception e){
            throw new ProductNotFoundException("Invalid Product Id !!");
        }
        Item item = Item.builder()
                .requiredQuantity(0)
                .product(product)
                .build();

        //map item to product
         product.setItem(item);

         //save both product and item
         productRepository.save(product);


        //Prepare response Dto
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();

           return itemResponseDto;
    }
}
