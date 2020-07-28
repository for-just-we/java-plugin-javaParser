package com.ssca.rules.timetask;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.ssca.bean.AnnotaionBean;
import com.ssca.enums.Rules;
import com.ssca.rules.RuleDefine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeAnnotationCheck extends RuleDefine {

    private List<String> annotationNotToUse = Arrays.asList("Scheduled");

    private List<AnnotaionBean> annotaionBeans = new ArrayList<>();

    @Override
    public List<AnnotaionBean> getAnnotaionBeans() {
        return annotaionBeans;
    }

    @Override
    public void visit(MethodDeclaration n, JavaParserFacade arg) {
        super.visit(n, arg);
        NodeList<AnnotationExpr> annotaions = n.getAnnotations();
        if (annotaions.size() == 0)
            return;

        for(AnnotationExpr annotationExpr: annotaions){
            String annoName = annotationExpr.getName().toString();
            if (annotationNotToUse.contains(annoName))
                annotaionBeans.add(new AnnotaionBean(annoName,
                        annotationExpr.getBegin().get().line," ",
                        Rules.AnnotationCheck));
        }
    }

}
