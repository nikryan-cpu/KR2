package controller;

import model.BinaryTreeMyModel;
import strategy.LeftMostMinStrategy;
import strategy.MinStrategy;
import strategy.StreamMinStrategy;
import view.TreeView;
import visitor.PrintVisitor;

import java.awt.event.ActionListener;

public class TreeController {
    private BinaryTreeMyModel model;
    private TreeView view;
    private MinStrategy streamMinStrategy;
    private MinStrategy leftmostMinStrategy;

    public TreeController(BinaryTreeMyModel model, TreeView view) {
        this.model = model;
        this.view = view;
        this.streamMinStrategy = new StreamMinStrategy();
        this.leftmostMinStrategy = new LeftMostMinStrategy();

        // Назначение обработчиков событий
        view.addActionListener("Добавить", e -> {
            try {
                int value = Integer.parseInt(view.getInput());
                model.add(value);
                view.appendOutput("Добавлено: " + value);
                view.clearInputField();
            } catch (NumberFormatException ex) {
                view.appendOutput("Введите корректное число.");
            }
        });

        view.addActionListener("Дерево", e -> {
            view.appendOutput("Дерево: " + model.getTree());
        });

        view.addActionListener("Минимум (Stream)", e -> {
            Integer minValue = streamMinStrategy.findMin(model);
            view.appendOutput("Минимальное значение (Stream): " + minValue);
        });

        view.addActionListener("Минимум (Левый)", e -> {
            Integer minValue = leftmostMinStrategy.findMin(model);
            view.appendOutput("Минимальное значение (Левый): " + minValue);
        });

        view.addActionListener("Путь к мин.", e -> {
            view.appendOutput("Путь к минимальному: " + model.moveToMin());
        });

        view.addActionListener("Префиксный", e -> {
            view.appendOutput("Префиксный обход: " + model.prefixTraversal());
        });

        view.addActionListener("Сохранить", e -> {
            model.saveToFile("tree.txt");
            view.appendOutput("Дерево сохранено в файл tree.txt");
        });

        view.addActionListener("Обход (Visitor)", e -> {
            PrintVisitor visitor = new PrintVisitor();
            model.accept(visitor);
            view.appendOutput("Обход выполнен: " + visitor.getOutput());
        });
    }
}