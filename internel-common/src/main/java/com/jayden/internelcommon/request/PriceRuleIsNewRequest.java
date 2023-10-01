package com.jayden.internelcommon.request;

import lombok.Data;

@Data
public class PriceRuleIsNewRequest {

    private String fareType;

    private Integer fareVersion;
}
