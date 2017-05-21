package smas.solver.aggregators;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// TODO delete in future versions
@Deprecated
public class TestAggregator implements VariableAggregator {

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
