package com.smartkhata.auth.service.impl;

import com.smartkhata.auth.dto.VendorDto;
import com.smartkhata.auth.dto.VendorResponseDto;

import java.util.List;

public interface VendorService {
    VendorDto create(VendorDto dto);
    VendorDto get(Long id);
    List<VendorDto> getAll();
    VendorDto update(Long id, VendorDto dto);
    void delete(Long id);
    VendorResponseDto getById(Long vendorId);

}
