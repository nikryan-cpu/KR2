package model;

import java.util.List;

public class StreamMinStrategy implements FindMinStrategy {

    @Override
    public int findMin(List<Integer> tree) {
        return tree.stream()
                .filter(value -> value != 0)
                .min(Integer::compareTo)
                .orElse(0);
    }
}