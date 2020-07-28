package com.ssca.rules.timetask;

import com.ssca.util.DirExplorer;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TimeApiTest {

    @Test
    public void check() throws IOException {
        DirExplorer dirExplorer = new DirExplorer("lib");
        dirExplorer.addRule(Arrays.asList(new TimeApi(),
                new TimeAnnotationCheck(),
                new IterationCheck()
                ));
        File projectDir = new File("src/test/testProject/timeTask");

        dirExplorer.explore(projectDir);
    }
}