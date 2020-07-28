package com.ssca.util.ruleUtils;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.APIBean;
import com.ssca.enums.Rules;
import com.ssca.rules.RuleDefine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhileBlockCheck extends RuleDefine {
    private List<APIBean> apiList = new ArrayList<>();

    private List<String> classInBlock = Arrays.asList("java.lang.Thread");
    private List<String> methodInBlock = Arrays.asList("sleep");

    @Override
    public List<APIBean> getApiList() {
        return apiList;
    }

    @Override
    public void visit(MethodCallExpr n, JavaParserFacade arg) {
        String methodName = n.getName().toString();
        String className = " ";
        try{
            className = arg.getType(n.getScope().get()).describe();
        }catch (UnsolvedSymbolException e){
            System.out.println(e.getName());
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        if (classInBlock.contains(className) && methodInBlock.contains(methodName))
            apiList.add(new APIBean(methodName, className, n.getBegin().get().line,
                    " ",Rules.WhileTrueCheck));

        super.visit(n, arg);
    }
}
