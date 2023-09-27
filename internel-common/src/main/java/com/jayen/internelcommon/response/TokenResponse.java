package com.jayen.internelcommon.response;

import lombok.Data;

@Data
public class TokenResponse {

    private String accessToken;

    private String freshToken;

}
