package com.smartkhata.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponseDto {

    private Long id;
    private String shopName;
    private String ownerName;
    private String phone;
}
