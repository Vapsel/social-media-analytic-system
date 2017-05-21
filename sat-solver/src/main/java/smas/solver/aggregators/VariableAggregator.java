package smas.solver.aggregators;

import java.util.Collection;

public interface VariableAggregator {

    /**
     * Aggregates all preferences in categories
     * @return Categories with preferences
     */
    Collection<Collection<String>> aggregateAll();
}
