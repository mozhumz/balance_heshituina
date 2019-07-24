package com.mozhumz.balance.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductUserDto {
    private Long userId;

    private List<Long> productIdList;
}
