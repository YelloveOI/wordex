package rsp.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegistrationResponse {
    private final String accessToken;
}