package com.smas.solver;

import com.smas.solver.aggregators.IVarAggregator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

public class CnfFileCreator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final String CNF_FILE_SUFFIX = "_smas_problem.cnf";

    /**
     * Character to indicate CNF formula ending
     */
    private static final int CNF_FORMULA_END = 0;

    /**
     * First line of the cnf file, where first <code>int</code> is highest number (variable) in the clauses and
     * the second <code>int</code> is count of clauses (lines) in the file.
     */
    private static final String FIRST_LINE_FORMAT = "p cnf %d %d";

    public Path transformToCnfFile(IVarAggregator aggregator, Map<Integer, String> varMap) throws IOException {

        Collection<Collection<String>> prefCategories = aggregator.aggregateAll();
        if (prefCategories == null){
            throw new IllegalArgumentException("Aggregator returns null");
        }

        List<String> lines = new ArrayList<>(prefCategories.size() * 2);
        int i = 0;
        for(Collection<String> category : prefCategories){

            StringBuilder commentLine = new StringBuilder("c ");
            StringBuilder formulaLine = new StringBuilder("");
            for (String pref : category){
                commentLine.append(pref);
                commentLine.append(" ");
                formulaLine.append(++i);
                formulaLine.append(" ");

                varMap.put(i, pref);
            }
            formulaLine.append(CNF_FORMULA_END);
            lines.add(commentLine.toString());
            lines.add(formulaLine.toString());
        }
        String lineOfProblem = String.format(FIRST_LINE_FORMAT, i, prefCategories.size());
        lines.add(0, lineOfProblem);

        return writeFile(lines);
    }

    private Path writeFile(List<String> lines) throws IOException {

        String filename = LocalDateTime.now().format(formatter) + CNF_FILE_SUFFIX;
        return Files.write(Paths.get("./" + filename), lines, CREATE_NEW, WRITE);
    }

}
