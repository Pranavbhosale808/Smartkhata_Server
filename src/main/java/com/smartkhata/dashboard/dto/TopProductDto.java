package com.smartkhata.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopProductDto {

    private String productName;
    private Long quantity;
}
