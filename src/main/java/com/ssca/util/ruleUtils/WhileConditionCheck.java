package com.ssca.util.ruleUtils;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.APIBean;
import com.ssca.enums.Rules;
import com.ssca.rules.RuleDefine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhileConditionCheck extends RuleDefine {
    private List<APIBean> apiList = new ArrayList<>();

    private List<String> classInCondition = Arrays.asList("java.lang.System");
    private List<String> methodInCondition = Arrays.asList("currentTimeMillis");

    @Override
    public List<APIBean> getApiList() {
        return apiList;
    }

    @Override
    public void visit(BinaryExpr n, JavaParserFacade arg) {
        if (!n.getLeft().isBinaryExpr())
            return;
        BinaryExpr expr = (BinaryExpr)n.getLeft();

        if (!expr.getLeft().isMethodCallExpr())
            return;

        MethodCallExpr callExpr = (MethodCallExpr) expr.getLeft();

        String methodName = callExpr.getName().toString();
        String className = " ";
        try{
            className = arg.getType(callExpr.getScope().get()).describe();
        }catch (UnsolvedSymbolException e){
            System.out.println(e.getName());
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        if (classInCondition.contains(className) &&
                methodInCondition.contains(methodName))
            apiList.add(new APIBean(methodName, className, n.getBegin().get().line,
                    " ",Rules.WhileConditionCheck));

        super.visit(n, arg);
    }
}
