package com.smas.solver.aggregators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vadym on 4/11/2017.
 */
public class TestAggregator implements IVarAggregator {

    @Override
    public Collection<Collection<String>> aggregateAll() {
        Set<Collection<String>> categories = new HashSet<>();
        categories.add(new HashSet<>());
        return categories;
    }
}
