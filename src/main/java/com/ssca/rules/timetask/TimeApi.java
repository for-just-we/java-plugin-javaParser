package com.ssca.rules.timetask;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.APIBean;
import com.ssca.enums.Rules;
import com.ssca.rules.RuleDefine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TimeApi extends RuleDefine {
    private List<String> checkApiList = Arrays.asList("scheduleAtFixedRate",
            "scheduleWithFixedDelay","schedule","start");
    private List<String> checkClassList = Arrays.asList("java.util.Timer",
            "java.util.concurrent.ScheduledThreadPoolExecutor",
            "java.util.concurrent.ScheduledExecutorService",
            "org.quartz.Scheduler");

    private List<APIBean> apiList = new ArrayList<>();

    @Override
    public void visit(MethodCallExpr n, JavaParserFacade javaParserFacade) {
        String methodName = n.getName().toString();
        String className = " ";
        try{
            className = javaParserFacade.getType(n.getScope().get()).describe();
        }catch (UnsolvedSymbolException e){
            System.out.println(e.getName());
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        if (checkApiList.contains(methodName) && checkClassList.contains(className))
            this.apiList.add(new APIBean(methodName, className, n.getBegin().get().line,
                    " ",Rules.TimeApi));

        super.visit(n, javaParserFacade);
    }

    public List<APIBean> getApiList() {
        return apiList;
    }
}
