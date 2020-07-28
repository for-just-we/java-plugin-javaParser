package com.ssca.bean;

import com.ssca.enums.Rules;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIBean {
    private String apiName;
    private String referenceClass;
    private int line;
    private String msg;

    private Rules rule;
}
