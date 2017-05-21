package smas.solver;

import smas.solver.aggregators.VariableAggregator;

import java.io.IOException;

public interface SATSolverManager {

    /**
     * Resolve satisfiable problem
     * @param aggregator Aggregates all preferences in categories
     * @return Answer of the problem
     * @throws IOException *
     */
    String resolveSatisfiableProblem(VariableAggregator aggregator) throws IOException;
}
