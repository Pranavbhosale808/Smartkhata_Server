package com.smartkhata.common.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private boolean success;
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
