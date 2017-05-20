package com.smas.solver;

import com.smas.solver.aggregators.VariableAggregator;

import java.io.IOException;

public interface SATSolverManager {

    String resolveSatisfiableProblem(VariableAggregator aggregator) throws IOException;
}
