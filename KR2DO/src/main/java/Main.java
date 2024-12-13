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
        setSize(800, 400); // Размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);

        // Установка GridLayout для кнопок
        inputPanel.setLayout(new GridLayout(2, 6, 10, 10)); // 2 ряда, 6 колонок, отступы

        // Создание кнопок
        JButton addButton = new JButton("Добавить");
        JButton displayTreeButton = new JButton("Отобразить");
        JButton displayMinButton = new JButton("Минимум");
        JButton displayPathButton = new JButton("Путь к мин.");
        JButton displayPrefixButton = new JButton("Префиксный обход");
        JButton saveButton = new JButton("Сохранить в файл");

        // Установка фиксированной ширины для кнопок
        Dimension buttonSize = new Dimension(120, 30);
        addButton.setPreferredSize(buttonSize);
        displayTreeButton.setPreferredSize(buttonSize);
        displayMinButton.setPreferredSize(buttonSize);
        displayPathButton.setPreferredSize(buttonSize);
        displayPrefixButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        // Добавление текстового поля и кнопок на панель
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(displayTreeButton);
        inputPanel.add(displayMinButton);
        inputPanel.add(displayPathButton);
        inputPanel.add(displayPrefixButton);
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Обработчики событий для кнопок
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(inputField.getText());
                model.add(value);
                outputArea.append("Добавлено: " + value + "\n");
                inputField.setText("");
            }
        });

        displayTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOutput("Дерево: " + model.getTree());
            }
        });

        displayMinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOutput("Минимальное значение: " + model.min());
            }
        });

        displayPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOutput("Путь к минимальному: " + model.moveToMin());
            }
        });

        displayPrefixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOutput("Префиксный обход: " + model.prefixTraversal());
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

    public void updateOutput(String message) {
        outputArea.append(message + "\n");
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