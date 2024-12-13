package controller;

import com.sun.source.tree.BinaryTree;
import model.BinaryTreeModel;
import view.BinaryTreeView;

public class BinaryTreeController {
    private BinaryTreeModel model;
    private BinaryTreeView view;

    public BinaryTreeController(BinaryTreeModel model, BinaryTreeView view) {
        this.model = model;
        this.view = view;
    }

    public void addValue(int value) {
        model.add(value);
        updateView();
    }

    public void updateView() {
        view.displayTree(model.getTree());
        view.displayMin(model.min());
        view.displayPath(model.moveToMin());
        view.displayPrefixTraversal(model.prefixTraversal());
    }
}