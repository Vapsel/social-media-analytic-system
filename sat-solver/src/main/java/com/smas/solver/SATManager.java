package com.smas.solver;

import com.smas.solver.aggregators.TestAggregator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sat4j.pb.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SATManager {

    /**
     * 1 hour timeout for solver
     */
    private final static int TIMEOUT_MINUTES = 3600;

    private static final Logger logger = LogManager.getLogger(SATManager.class.getName());

    // TODO replace static
    public static void main(String[] args) throws IOException, ParseFormatException {

        PrintStream out = new PrintStream(System.out, true, Charset.forName("UTF-8").name());

        TestAggregator testAggregator = new TestAggregator();
        CnfFileCreator creator = new CnfFileCreator();
        Map<Integer, String> varMap = new HashMap<>();
        Path path = creator.transformToCnfFile(testAggregator, varMap);

        int[] model = runSatSolver(path);
        if (model == null){
            throw new IllegalStateException("File can't be processed by SAT solver");
        }

        out.println("Satisfiability problem can be solved in following way:");
        out.println(composeAnswer(model, varMap));

    }

    // TODO replace static
    private static int[] runSatSolver(Path path){
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(TIMEOUT_MINUTES);
        Reader reader = new DimacsReader(solver);

        IProblem problem = null;
        try {
            problem = reader.parseInstance(path.toString());
            if (problem.isSatisfiable()) {
                return problem.model();
            } else {
                logger.error("Problem described in " + path.getFileName() + " is unsatisfiable");
            }
        } catch (ContradictionException | TimeoutException | ParseFormatException | IOException e) {
            logger.error("Error during SAT-solver processing", e);
        }
        return null;
    }

    /**
     * Compose result string based on model
     * @param model Result model from SAT solver
     * @param variableMap Map of variables that were used in SAT problem
     * @return Readable result string
     */
    private static String composeAnswer(int[] model, Map<Integer, String> variableMap){

        StringBuilder resultString = new StringBuilder();
        for (Integer i: model){
            if (i < 0){
                resultString.append("-");
            }
            resultString.append(variableMap.get(Math.abs(i)));
            resultString.append(" ");
        }
        return resultString.toString();
    }
}
