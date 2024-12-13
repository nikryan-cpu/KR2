package model;// BinaryTreeMyModel.java
import com.sun.source.tree.TreeVisitor;
import visitor.PrintVisitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryTreeMyModel {
    private ArrayList<Integer> tree;

    public BinaryTreeMyModel() {
        tree = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            tree.add(0);
        }
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

            if (index >= tree.size()) {

                for (int i = tree.size(); i <= index; i++) {
                    tree.add(0);
                }
            }
        }
    }
    public List<Integer> getTree() {
        return tree;
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer value : tree) {
                writer.write(value + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> prefixTraversal() {
        List<Integer> result = new ArrayList<>();
        prefixHelper(0, result);
        return result;
    }

    private void prefixHelper(int index, List<Integer> result) {
        if (index < tree.size()) {
            if (tree.get(index) != 0) {
                result.add(tree.get(index));
            }
            prefixHelper(2 * index + 1, result); // левый сын
            prefixHelper(2 * index + 2, result); // правый сын
        }
    }

    public List<Integer> moveToMin() {
        List<Integer> path = new ArrayList<>();
        int index = 0;
        while (index < tree.size() && tree.get(index) != 0) {
            path.add(tree.get(index));
            index = 2 * index + 1; // Идем к левому сыну
        }
        return path;
    }

    public void accept(PrintVisitor visitor) {
        for (Integer value : tree) {
            if (value != 0) {
                visitor.visit(value);
            }
        }
    }
}