package com.place.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDTO {
    private Long id;
    private String username;
    private String title;
    private LocalDate date;
    private Boolean maxCostEnable;
    private Integer maxCost;
    private Integer type;
    private String detail;
    private String file;
}
