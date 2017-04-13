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

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final String CNF_FILE_SUFFIX = "_smas_problem.cnf";

    public void transformToCnfFile(IVarAggregator aggregator){
        // todo return path to file

        Collection<Collection<String>> prefCategories = aggregator.aggregateAll();
        if (prefCategories == null){
            throw new IllegalArgumentException("Aggregator can't return null");
        }

        List<String> lines = new ArrayList<>(prefCategories.size() * 2);
        // todo use indexes as varNumber
        int i = 0;
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

        writeFile(lines);
    }

    private void writeFile(List<String> lines){

        String filename = LocalDateTime.now().format(formatter) + CNF_FILE_SUFFIX;
        try {
            Files.write(Paths.get("./" + filename), lines, CREATE_NEW, WRITE);
        } catch (IOException e) {
            // // TODO: 4/11/2017
            e.printStackTrace();
        }
    }

}
