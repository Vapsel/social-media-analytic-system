package com.smas.solver;

import com.smas.solver.aggregators.IVarAggregator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Created by Vadym on 4/11/2017.
 */
public class CnfFileCreator {

    public void createCnfFile(IVarAggregator aggregator){

        Collection<Collection<String>> prefCategories = aggregator.aggregateAll();
        if (prefCategories == null){
            throw new IllegalArgumentException("Parameter can't be null");
        }

        int i = 0;
        List<String> lines = new ArrayList<>(); // todo initial capacity
        //init line
        for(Collection<String> category : prefCategories){

            StringBuilder commentLine = new StringBuilder("c ");
            StringBuilder formulaLine = new StringBuilder("");
            for (String pref : category){
                commentLine.append(pref);
                commentLine.append(" ");
                formulaLine.append(++i);
                formulaLine.append(" ");
            }
            formulaLine.append(0);
            lines.add(commentLine.toString());
            lines.add(formulaLine.toString());
        }
        String problemLine = String.format("p cnf %d %d", i, prefCategories.size());
        lines.add(0, problemLine);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
        String filename = LocalDateTime.now().format(formatter) + "_smas_problem.cnf";
        try {
            Files.write(Paths.get("./" + filename), lines, CREATE_NEW, WRITE);
        } catch (IOException e) {
            // // TODO: 4/11/2017
            e.printStackTrace();
        }
    }


}
