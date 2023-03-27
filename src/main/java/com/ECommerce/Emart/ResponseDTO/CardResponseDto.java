package com.ECommerce.Emart.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

     private String CustomerName;

     List<CardDto> cardDtos;
}
