package com.ssca.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Rules {
    TimeApi("调用敏感api","定时任务",true, Arrays.asList("敏感api","定时"),
            "medium","java-time-001","java",9),

    WhileTrueCheck("while(true)中调用sleep","定时任务",true,
            Arrays.asList("死循环","sleep"),"medium","java-time-002",
            "java",9),

    WhileConditionCheck("while循环条件调用计时api","定时任务",true,
            Arrays.asList("循环","计时"),"medium","java-time-003","java",
            9),

    AnnotationCheck("调用与定时任务有关的注解","定时任务",true,
            Arrays.asList("注解"),"medium","java-time-004","java",
            8)
    ;
    private String title;
    private String category;
    private boolean enabled;
    private List<String> tag;
    private String severity;
    private String name;
    private String language;
    private int accuracy;
}