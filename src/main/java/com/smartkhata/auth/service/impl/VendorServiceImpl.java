package com.smartkhata.auth.service.impl;

import com.smartkhata.auth.dto.VendorDto;
import com.smartkhata.auth.dto.VendorResponseDto;
import com.smartkhata.auth.entity.Vendor;
import com.smartkhata.auth.repository.VendorRepository;
import com.smartkhata.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final ModelMapper mapper;

    @Override
    public VendorResponseDto getById(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        return mapper.map(vendor, VendorResponseDto.class);
    }

    @Override
    public VendorDto create(VendorDto dto) {
        Vendor vendor = mapper.map(dto, Vendor.class);
        return mapper.map(vendorRepository.save(vendor), VendorDto.class);
    }

    @Override
    public VendorDto get(Long id) {
        return mapper.map(
                vendorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found")),
                VendorDto.class
        );
    }

    @Override
    public List<VendorDto> getAll() {
        return vendorRepository.findAll()
                .stream()
                .map(v -> mapper.map(v, VendorDto.class))
                .toList();
    }

    @Override
    public VendorDto update(Long id, VendorDto dto) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        mapper.map(dto, vendor);
        return mapper.map(vendorRepository.save(vendor), VendorDto.class);
    }

    @Override
    public void delete(Long id) {
        vendorRepository.deleteById(id);
    }
}
