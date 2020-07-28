package com.ssca.util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.ssca.bean.APIBean;
import com.ssca.bean.AnnotaionBean;
import com.ssca.rules.RuleDefine;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirExplorer {
    private List<RuleDefine> rules;
    private TypeSolver typeSolver;

    private Map<String, List<APIBean> > apiBeanList = new HashMap<>();
    private Map<String, List<AnnotaionBean> > annotaionBeanList = new HashMap<>();

    private String libPath;

    public DirExplorer(String libPath) {
        this.libPath = libPath;
        this.rules = new ArrayList<>();
    }

    public void addRule(RuleDefine rule){
        this.rules.add(rule);
    }

    public void addRule(List<RuleDefine> rules){
        this.rules.addAll(rules);
    }

    public void explore(File root) throws IOException {
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(new JavaParserTypeSolver(root));
        typeSolver.add(new ReflectionTypeSolver());

        for(File child : new File(libPath).listFiles()){
            if (child.getPath().endsWith(".jar"))
                typeSolver.add(new JarTypeSolver(child.getPath()));
        }

        this.typeSolver = typeSolver;
        explore(0, "", root);
    }

    private void explore(int level, String path, File file) {
        if (file.isDirectory()){
            for (File child : file.listFiles())
                explore(level + 1, path + "/" + child.getName(), child);

        } else {
            if (interested(path))
               parse(path, file);
        }
    }

    private void parse(String path, File file){
        try {
            CompilationUnit ast = StaticJavaParser.parse(file);
            List<String> msgs = new ArrayList<>();
            List<APIBean> apiBeans = new ArrayList<>();
            List<AnnotaionBean> annotaionBeans = new ArrayList<>();


            for (RuleDefine rule : this.rules){
                ast.accept(rule, JavaParserFacade.get(this.typeSolver));
                rule.getApiList().forEach(n->{n.setMsg("method:"
                        + n.getReferenceClass() + "." + n.getApiName()
                        + " detected at line:" + n.getLine()
                        + " rule id:" + n.getRule().getName());

                        apiBeans.add(n);});

                rule.getApiList().clear();

                rule.getAnnotaionBeans().forEach(n->{n.setMsg("annotation:"
                        + n.getAnnoName()
                        + " detected at line:" + n.getLine()
                        + " rule id:" + n.getRule().getName());

                        annotaionBeans.add(n);});

                rule.getAnnotaionBeans().clear();

                if (apiBeans.size() > 0)
                    this.apiBeanList.put(path, apiBeans);

                if (annotaionBeans.size() > 0)
                    this.annotaionBeanList.put(path, annotaionBeans);
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件目录设置错误:" + e.getMessage());
        }
    }

    private boolean interested(String path){
        return path.endsWith(".java");
    }

    public Map<String, List<APIBean>> getApiBeanList() {
        return apiBeanList;
    }

    public Map<String, List<AnnotaionBean>> getAnnotaionBeanList() {
        return annotaionBeanList;
    }
}
