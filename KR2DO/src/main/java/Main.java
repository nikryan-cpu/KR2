import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//
interface MinStrategy {
    Integer findMin(BinaryTreeMyModel tree);
}

// Concrete Strategy for Stream API
class StreamMinStrategy implements MinStrategy {
    @Override
    public Integer findMin(BinaryTreeMyModel tree) {
        return tree.getTree().stream()
                .filter(value -> value != 0) // Игнорируем нули
                .min(Integer::compareTo)
                .orElse(null);
    }
}

// Concrete Strategy for Leftmost Child
class LeftmostMinStrategy implements MinStrategy {
    @Override
    public Integer findMin(BinaryTreeMyModel tree) {
        int index = 0;
        while (index < tree.getTree().size() && tree.getTree().get(index) != 0) {
            index = 2 * index + 1; // Идем к самому левому сыну
        }
        return index > 0 ? tree.getTree().get((index - 1) / 2) : null;
    }
}

// Model
class BinaryTreeMyModel {
    private ArrayList<Integer> tree;

    public BinaryTreeMyModel() {
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

    // Новый метод для получения пути до минимума
    public List<Integer> moveToMin() {
        List<Integer> path = new ArrayList<>();
        int index = 0;
        while (index < tree.size() && tree.get(index) != 0) {
            path.add(tree.get(index));
            index = 2 * index + 1; // Идем к левому сыну
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
    private BinaryTreeMyModel model;
    private JTextArea outputArea;
    private JTextField inputField;

    // Стратегии для поиска минимума
    private MinStrategy streamMinStrategy;
    private MinStrategy leftmostMinStrategy;

    public TreeView(BinaryTreeMyModel model) {
        this.model = model;
        this.streamMinStrategy = new StreamMinStrategy();
        this.leftmostMinStrategy = new LeftmostMinStrategy();

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
        JButton displayTreeButton = new JButton("Дерево");
        JButton displayMinStreamButton = new JButton("Минимум (Stream)");
        JButton displayMinLeftmostButton = new JButton("Минимум (Левый)");
        JButton displayPathButton = new JButton("Путь к мин.");
        JButton displayPrefixButton = new JButton("Префиксный");
        JButton saveButton = new JButton("Сохранить");

        // Установка фиксированной ширины для кнопок
        Dimension buttonSize = new Dimension(120, 30);
        addButton.setPreferredSize(buttonSize);
        displayTreeButton.setPreferredSize(buttonSize);
        displayMinStreamButton.setPreferredSize(buttonSize);
        displayMinLeftmostButton.setPreferredSize(buttonSize);
        displayPathButton.setPreferredSize(buttonSize);
        displayPrefixButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        // Добавление текстового поля и кнопок на панель
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(displayTreeButton);
        inputPanel.add(displayMinStreamButton);
        inputPanel.add(displayMinLeftmostButton);
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

        displayMinStreamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer minValue = streamMinStrategy.findMin(model);
                updateOutput("Минимальное значение (Stream): " + minValue);
            }
        });

        displayMinLeftmostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer minValue = leftmostMinStrategy.findMin(model);
                updateOutput("Минимальное значение (Левый): " + minValue);
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
        BinaryTreeMyModel tree = new BinaryTreeMyModel();
        TreeView view = new TreeView(tree);
        view.setVisible(true);
    }
}