package com.smas.solver.aggregators;

import java.util.Arrays;
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
        Set<String> locations = new HashSet<>();
        Set<String> food = new HashSet<>();

        locations.addAll(Arrays.asList("Krakow", "Warszawa", "Gdansk"));
        food.addAll(Arrays.asList("Pizza", "Burgery", "Wino"));

        categories.addAll(Arrays.asList(locations, food));
        return categories;
    }
}
