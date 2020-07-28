package com.ssca.rules.timetask;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IterationCheckTest {
    @Test
    public void checkFile() throws FileNotFoundException {
        CombinedTypeSolver typeSolver = new CombinedTypeSolver(
                new JavaParserTypeSolver(new File("src/test/testProject/timeTask/src/main/java")));
        typeSolver.add(new ReflectionTypeSolver());

        IterationCheck iterationCheck = new IterationCheck();
        CompilationUnit agendaCu = StaticJavaParser
                .parse(new FileInputStream(
                        new File("src/test/testProject/timeTask/src/main/java/whileStmt/IterTest.java")));
        agendaCu.accept(iterationCheck, JavaParserFacade.get(typeSolver));

        iterationCheck.getApiList().forEach(n -> {System.out.println("method:"
                + n.getReferenceClass() + "." + n.getApiName()
                + " detected at line:" + n.getLine());});
    }
}