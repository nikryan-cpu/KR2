package model;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeModel {
    private ArrayList<Integer> tree;

    public BinaryTreeModel() {
        tree = new ArrayList<>();
        tree.add(10); // корень
    }

    public void add(int value) {
        int index = 0;
        while (index < tree.size()) {
            if (tree.get(index) == 0) {
                tree.set(index, value);
                return;
            }
            if (value < tree.get(index)) {
                index = 2 * index + 1; // левый сын
            } else {
                index = 2 * index + 2; // правый сын
            }
        }
        tree.add(value); // добавляем в конец, если вышли за пределы
    }

    public List<Integer> prefixTraversal() {
        List<Integer> result = new ArrayList<>();
        prefixHelper(0, result);
        return result;
    }

    private void prefixHelper(int index, List<Integer> result) {
        if (index < tree.size() && tree.get(index) != 0) {
            result.add(tree.get(index));
            prefixHelper(2 * index + 1, result); // левый сын
            prefixHelper(2 * index + 2, result); // правый сын
        }
    }

    public Integer min() {
        return minHelper(0);
    }

    private Integer minHelper(int index) {
        while (index < tree.size() && tree.get(index) != 0) {
            index = 2 * index + 1; // идем к самому левому сыну
        }
        return tree.get((index - 1) / 2); // возвращаем значение
    }

    public List<Integer> moveToMin() {
        List<Integer> path = new ArrayList<>();
        int index = 0;
        while (index < tree.size() && tree.get(index) != 0) {
            path.add(tree.get(index));
            index = 2 * index + 1; // идем к левому сыну
        }
        return path;
    }

    public List<Integer> getTree() {
        return tree;
    }
}