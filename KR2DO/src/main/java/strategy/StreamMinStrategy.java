package strategy;

import model.BinaryTreeMyModel;

public class StreamMinStrategy implements MinStrategy {
    @Override
    public Integer findMin(BinaryTreeMyModel tree) {
        return tree.getTree().stream()
                .filter(value -> value != 0) // Игнорируем нули
                .min(Integer::compareTo)
                .orElse(null);
    }
}