package com.example.model.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModelStatus {

    ACTIVE(1),
    INACTIVE(0);

    private final int statusCode;

}
