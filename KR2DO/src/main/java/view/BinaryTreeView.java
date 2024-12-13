package view;

import java.util.List;

public class BinaryTreeView {
    public void displayTree(List<Integer> tree) {
        System.out.println("Дерево: " + tree);
    }

    public void displayMin(Integer min) {
        System.out.println("Минимальное значение: " + min);
    }

    public void displayPath(List<Integer> path) {
        System.out.println("Путь к минимальному: " + path);
    }

    public void displayPrefixTraversal(List<Integer> traversal) {
        System.out.println("Префиксный обход: " + traversal);
    }
}