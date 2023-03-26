package com.ECommerce.Emart.ResponseDTO;

import com.ECommerce.Emart.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private String productName;

    private int quantity;

    private int price;

    private ProductStatus productStatus;
}
