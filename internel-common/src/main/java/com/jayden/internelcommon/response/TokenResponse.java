package com.jayden.internelcommon.response;

import lombok.Data;

@Data
public class TokenResponse {

    private String accessToken;

    private String freshToken;

}
