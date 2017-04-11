package com.smas.solver;

import com.smas.solver.CnfFileCreator;
import com.smas.solver.aggregators.TestAggregator;
import org.sat4j.pb.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileExample {

    public static void main(String[] args) {
        TestAggregator testAggregator = new TestAggregator();
        CnfFileCreator creator = new CnfFileCreator();
        creator.createCnfFile(testAggregator);

        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        // CNF filename is given on the command line
        String filename = "D:\\GitHub\\social-media-analytic-system\\solver\\example.cnf";
        try {
            IProblem problem = reader.parseInstance(filename);
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                System.out.println(reader.decode(problem.model()));
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (ParseFormatException | IOException e) {
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }
        // niepoprawny zapis - odrazu wylatuje
    }
}
