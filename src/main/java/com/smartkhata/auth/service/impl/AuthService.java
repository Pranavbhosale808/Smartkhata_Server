package com.smartkhata.auth.service.impl;

import com.smartkhata.auth.dto.*;

public interface AuthService {

    AuthResponseDto ownerSignup(OwnerSignupDto dto);

    AuthResponseDto staffSignup(StaffSignupDto dto);

    AuthResponseDto login(LoginDto dto);
}
