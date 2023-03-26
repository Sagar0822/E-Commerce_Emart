package com.ECommerce.Emart.RequestDto;

import com.ECommerce.Emart.Enum.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    private int SellerId;

    private String productName;

    private int price;

    private int quantity;

    private ProductCategory productCategory;
}
