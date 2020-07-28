package com.ssca.rules.timetask;

import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.APIBean;
import com.ssca.rules.RuleDefine;
import com.ssca.util.ruleUtils.WhileBlockCheck;
import com.ssca.util.ruleUtils.WhileConditionCheck;

import java.util.ArrayList;
import java.util.List;

public class IterationCheck extends RuleDefine {
    private List<APIBean> apiList = new ArrayList<>();

    @Override
    public List<APIBean> getApiList() {
        return apiList;
    }

    @Override
    public void visit(WhileStmt n, JavaParserFacade arg) {
        if (n.getCondition().isBooleanLiteralExpr() &&
                n.getCondition().toString().equals("true")){ //检测while(true)是否调用Thread.Sleep
            WhileBlockCheck whileBlockCheck = new WhileBlockCheck();
            n.getBody().accept(whileBlockCheck, arg);
            whileBlockCheck.getApiList().forEach(var -> {this.apiList.add(var);});

        } else if (n.getCondition().isBinaryExpr()){ //
            WhileConditionCheck conditionCheck = new WhileConditionCheck();
            n.getCondition().accept(conditionCheck, arg);
            conditionCheck.getApiList().forEach(var -> {this.apiList.add(var);});
        }
        super.visit(n, arg);
    }
}
