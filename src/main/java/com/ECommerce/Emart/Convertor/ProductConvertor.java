package com.ECommerce.Emart.Convertor;

import com.ECommerce.Emart.Enum.ProductStatus;
import com.ECommerce.Emart.Model.Product;
import com.ECommerce.Emart.RequestDto.ProductRequestDto;
import com.ECommerce.Emart.ResponseDTO.ProductResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConvertor {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto){
         return Product.builder()
                 .productName(productRequestDto.getProductName())
                 .price(productRequestDto.getPrice())
                 .quantity(productRequestDto.getQuantity())
                 .productStatus(ProductStatus.AVAILABLE)
                 .productCategory(productRequestDto.getProductCategory())
                 .build();
    }
    public static ProductResponseDto productToProductResponseDto(Product product){
          return ProductResponseDto.builder()
                  .productName(product.getProductName())
                  .quantity(product.getQuantity())
                  .price(product.getPrice())
                  .productStatus(ProductStatus.AVAILABLE)
                  .build();
    }
}
