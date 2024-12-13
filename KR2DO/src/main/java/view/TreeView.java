package view;// TreeView.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class TreeView extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;

    public TreeView() {
        setTitle("Binary Tree Application");
        setSize(800, 400); // Размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        inputPanel.add(inputField);

        // Установка GridLayout для кнопок (2 ряда и 4 колонки)
        inputPanel.setLayout(new GridLayout(2, 4, 10, 10)); // 2 ряда, 4 колонки, отступы

        // Создание кнопок
        JButton addButton = new JButton("Добавить");
        JButton displayTreeButton = new JButton("Дерево");
        JButton displayMinStreamButton = new JButton("Минимум (Stream)");
        JButton displayMinLeftmostButton = new JButton("Минимум (Левый)");
        JButton displayPathButton = new JButton("Путь к мин.");
        JButton displayPrefixButton = new JButton("Префиксный");
        JButton saveButton = new JButton("Сохранить");
        JButton visitButton = new JButton("Обход (Visitor)"); // Новая кнопка

        // Добавление кнопок на панель
        inputPanel.add(addButton);
        inputPanel.add(displayTreeButton);
        inputPanel.add(displayMinStreamButton);
        inputPanel.add(displayMinLeftmostButton);
        inputPanel.add(displayPathButton);
        inputPanel.add(displayPrefixButton);
        inputPanel.add(saveButton);
        inputPanel.add(visitButton);

        add(inputPanel, BorderLayout.SOUTH);
    }

    public String getInput() {
        return inputField.getText();
    }

    public void appendOutput(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    public void clearInputField() {
        inputField.setText("");
    }

    public void addActionListener(String command, ActionListener listener) {
        for (Component comp : ((JPanel) getContentPane().getComponent(1)).getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals(command)) {
                ((JButton) comp).addActionListener(listener);
            }
        }
    }
}