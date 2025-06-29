package com.example.msalmacen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDTO {
    private Long materialId;
    private Double cantidad;
}
