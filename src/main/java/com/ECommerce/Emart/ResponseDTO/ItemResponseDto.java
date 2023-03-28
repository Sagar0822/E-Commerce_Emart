package com.ECommerce.Emart.ResponseDTO;

import com.ECommerce.Emart.Enum.ProductCategory;
import com.ECommerce.Emart.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {

    private String productName;

    private int price;

    private ProductStatus productStatus;

    private ProductCategory productCategory;
}
