package rsp.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class LoginResponse {
    private final String accessToken;
}
