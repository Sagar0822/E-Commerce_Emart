package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Convertor.ProductConvertor;
import com.ECommerce.Emart.Enum.ProductCategory;
import com.ECommerce.Emart.Exception.SellerNotFoundException;
import com.ECommerce.Emart.Model.Product;
import com.ECommerce.Emart.Model.Seller;
import com.ECommerce.Emart.Repository.ProductRepository;
import com.ECommerce.Emart.Repository.SellerRepository;
import com.ECommerce.Emart.RequestDto.ProductRequestDto;
import com.ECommerce.Emart.ResponseDTO.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ECommerce.Emart.Convertor.ProductConvertor.productRequestDtoToProduct;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException{

        Seller seller;
        try{
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        }
        catch(Exception e){
            throw new SellerNotFoundException("Invalid Seller Id");
        }

        Product product = ProductConvertor.productRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);  //update list

        sellerRepository.save(seller);  //both save

        ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);
        return productResponseDto;
    }
    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory productCategory){

        List<Product> products = productRepository.findAllByProductCategory(productCategory);

        //Create list by Dto
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }
       return productResponseDtos;
    }

}
