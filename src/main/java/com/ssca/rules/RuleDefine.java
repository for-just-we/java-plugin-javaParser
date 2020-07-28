package com.ssca.rules;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.APIBean;
import com.ssca.bean.AnnotaionBean;

import java.util.ArrayList;
import java.util.List;


public class RuleDefine extends VoidVisitorAdapter<JavaParserFacade> {
    private List<APIBean> apiList = new ArrayList<>();
    private List<AnnotaionBean> annotaionBeans = new ArrayList<>();

    public List<APIBean> getApiList() {
        return apiList;
    }

    public List<AnnotaionBean> getAnnotaionBeans() {
        return annotaionBeans;
    }
}
