package com.jayden.internelcommon.response;

import lombok.Data;

@Data
public class TerminalResponse {

    private String tid;

    private Long carId;

    private String longitude ;
    private String latitude ;
}
