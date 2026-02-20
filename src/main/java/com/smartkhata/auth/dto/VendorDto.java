package com.smartkhata.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDto {
    private String shopName;
    private String ownerName;
    private String phone;
}
