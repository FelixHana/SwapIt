package com.cswap.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stat {
    private String monthYear;
    private String  region;
    private Integer orderType;
    private Integer orderCount;
}
