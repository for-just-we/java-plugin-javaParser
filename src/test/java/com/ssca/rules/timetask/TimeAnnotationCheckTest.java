package com.ssca.rules.timetask;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TimeAnnotationCheckTest {
    @Test
    public void checkFile() throws IOException {
        CombinedTypeSolver typeSolver = new CombinedTypeSolver(
                new JavaParserTypeSolver(new File("src/test/testProject/timeTask/src/main/java")));
        typeSolver.add(new ReflectionTypeSolver());

        for(File child : new File("lib").listFiles()){
            if (child.getPath().endsWith(".jar"))
                typeSolver.add(new JarTypeSolver(child.getPath()));
        }

        TimeAnnotationCheck timeCheck = new TimeAnnotationCheck();
        CompilationUnit agendaCu = StaticJavaParser
                .parse(new FileInputStream(
                        new File("src/test/testProject/timeTask/src/main/java/spring/SpringTest.java")));
        agendaCu.accept(timeCheck, JavaParserFacade.get(typeSolver));


    }
}