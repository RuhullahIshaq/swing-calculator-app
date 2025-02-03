import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomSwingCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea historyArea;
    private double num1, num2, result, memory;
    private char operator;
    private boolean isDegreeMode = true; // Toggle between degrees and radians
    private ArrayList<String> history = new ArrayList<>();

    public CustomSwingCalculator() {
        setTitle("Custom Swing Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // History Panel
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.EAST);

        // Buttons Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = { "7", "8", "9", "/",
                             "4", "5", "6", "*",
                             "1", "2", "3", "-",
                             "C", "0", ".", "+",
                             "MS", "MR", "=", "√" };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        // Adding Trigonometric Buttons
        JButton sinButton = new JButton("sin");
        JButton cosButton = new JButton("cos");
        JButton tanButton = new JButton("tan");
        JButton modeButton = new JButton("Deg/Rad");

        sinButton.setFont(new Font("Arial", Font.BOLD, 18));
        cosButton.setFont(new Font("Arial", Font.BOLD, 18));
        tanButton.setFont(new Font("Arial", Font.BOLD, 18));
        modeButton.setFont(new Font("Arial", Font.BOLD, 18));

        sinButton.addActionListener(this);
        cosButton.addActionListener(this);
        tanButton.addActionListener(this);
        modeButton.addActionListener(this);

        // Adding trig buttons in a separate row
        JPanel trigPanel = new JPanel();
        trigPanel.setLayout(new GridLayout(1, 4, 5, 5));
        trigPanel.add(sinButton);
        trigPanel.add(cosButton);
        trigPanel.add(tanButton);
        trigPanel.add(modeButton);
        
        add(panel, BorderLayout.CENTER);
        add(trigPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9.]") || command.equals("MS") || command.equals("MR")) {
                display.setText(display.getText() + command);
            } else if (command.equals("C")) {
                display.setText("");
                num1 = num2 = result = 0;
                operator = ' ';
            } else if (command.equals("=")) {
                calculateResult();
            } else if (command.equals("√")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.sqrt(num1);
                display.setText(String.valueOf(result));
                addToHistory("√(" + num1 + ") = " + result);
            } else if (command.equals("MS")) {
                memory = Double.parseDouble(display.getText());
                display.setText("");
            } else if (command.equals("MR")) {
                display.setText(String.valueOf(memory));
            } else if (command.equals("sin")) {
                num1 = Double.parseDouble(display.getText());
                result = isDegreeMode ? Math.sin(Math.toRadians(num1)) : Math.sin(num1);
                display.setText(String.valueOf(result));
                addToHistory("sin(" + num1 + ") = " + result);
            } else if (command.equals("cos")) {
                num1 = Double.parseDouble(display.getText());
                result = isDegreeMode ? Math.cos(Math.toRadians(num1)) : Math.cos(num1);
                display.setText(String.valueOf(result));
                addToHistory("cos(" + num1 + ") = " + result);
            } else if (command.equals("tan")) {
                num1 = Double.parseDouble(display.getText());
                result = isDegreeMode ? Math.tan(Math.toRadians(num1)) : Math.tan(num1);
                display.setText(String.valueOf(result));
                addToHistory("tan(" + num1 + ") = " + result);
            } else if (command.equals("Deg/Rad")) {
                isDegreeMode = !isDegreeMode;
                display.setText(isDegreeMode ? "Degree Mode" : "Radian Mode");
            } else {
                num1 = Double.parseDouble(display.getText());
                operator = command.charAt(0);
                display.setText("");
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void calculateResult() {
        try {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = num1 / num2;
                    break;
                default: throw new Exception("Invalid Operation");
            }
            display.setText(String.valueOf(result));
            addToHistory(num1 + " " + operator + " " + num2 + " = " + result);
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void addToHistory(String entry) {
        history.add(entry);
        historyArea.append(entry + "\n");
    }

    public static void main(String[] args) {
        CustomSwingCalculator calculator = new CustomSwingCalculator();
        calculator.setVisible(true);
    }
}
