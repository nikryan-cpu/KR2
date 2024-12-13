import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Model
class BinaryTree {
    private ArrayList<Integer> tree;

    public BinaryTree() {
        tree = new ArrayList<>();
        // Инициализация дерева нулями
        for (int i = 0; i < 15; i++) {
            tree.add(0); // Заполняем массив нулями для первых 15 элементов
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
        }
        // Если массив заполнен, увеличиваем его размер
        tree.add(value);
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
        int index = 0;
        while (index < tree.size() && tree.get(index) != 0) {
            index = 2 * index + 1; // идем к самому левому сыну
        }
        return index > 0 ? tree.get((index - 1) / 2) : null; // возвращаем значение, если есть
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

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer value : tree) {
                writer.write(value + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// View
class TreeView extends JFrame {
    private BinaryTree model;
    private JTextArea outputArea;
    private JTextField inputField;

    public TreeView(BinaryTree model) {
        this.model = model;
        setTitle("Binary Tree Application");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        JButton addButton = new JButton("Добавить");
        JButton saveButton = new JButton("Сохранить");

        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(saveButton);
        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(inputField.getText());
                model.add(value);
                updateOutput();
                inputField.setText("");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.saveToFile("tree.txt");
                outputArea.append("Дерево сохранено в файл tree.txt\n");
            }
        });
    }

    public void updateOutput() {
        outputArea.setText("");
        outputArea.append("Дерево: " + model.getTree() + "\n");
        if (!model.getTree().isEmpty()) {
            outputArea.append("Минимальное значение: " + model.min() + "\n");
            outputArea.append("Путь к минимальному: " + model.moveToMin() + "\n");
            outputArea.append("Префиксный обход: " + model.prefixTraversal() + "\n");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        TreeView view = new TreeView(tree);
        view.setVisible(true);
    }
}