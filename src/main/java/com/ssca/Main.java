package com.ssca;

import com.ssca.rules.RuleDefine;
import com.ssca.rules.timetask.IterationCheck;
import com.ssca.rules.timetask.TimeAnnotationCheck;
import com.ssca.rules.timetask.TimeApi;
import com.ssca.util.DirExplorer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3)
            throw new RuntimeException("参数不够");


        File projectDir = new File(args[0]);
        File resultFile = new File(args[1]);

        if (!resultFile.exists())
            resultFile.createNewFile();


        DirExplorer dirExplorer = new DirExplorer(args[2]);

        List<RuleDefine> rules = Arrays.asList(new TimeApi(),
                new IterationCheck(),new TimeAnnotationCheck());
        dirExplorer.addRule(rules);

        dirExplorer.explore(projectDir);
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
    }
}
