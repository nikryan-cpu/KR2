package model;

import java.util.List;

public class LinearMinStrategy implements FindMinStrategy {

    @Override
    public int findMin(List<Integer> tree) {
        int min = Integer.MAX_VALUE;
        for (int value : tree) {
            if (value != 0 && value < min) {
                min = value;
            }
        }
        return min;
    }
}