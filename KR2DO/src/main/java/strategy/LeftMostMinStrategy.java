package strategy;

import model.BinaryTreeMyModel;

public class LeftMostMinStrategy implements strategy.MinStrategy {
    @Override
    public Integer findMin(BinaryTreeMyModel tree) {
        int index = 0;
        while (index < tree.getTree().size() && tree.getTree().get(index) != 0) {
            index = 2 * index + 1; // Идем к самому левому сыну
        }
        return index > 0 ? tree.getTree().get((index - 1) / 2) : null;
    }
}