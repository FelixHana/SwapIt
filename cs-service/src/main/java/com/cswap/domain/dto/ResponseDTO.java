package com.cswap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {
    private Long id;
    private Long requestId;
    private String userResponse;
    private String title;
    private LocalDateTime date;
    private Integer cost;
    private String detail;
    private String responseFile;
}
