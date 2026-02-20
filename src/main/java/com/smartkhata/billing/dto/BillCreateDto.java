package com.smartkhata.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillCreateDto {

    @NotBlank
    private String billNumber;

    @NotNull
    private LocalDate billDate;

    @NotBlank
    private String customerName;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String customerMobile;

    @NotBlank
    private String status; 

    @NotEmpty
    private List<@Valid BillItemCreateDto> items;
}

