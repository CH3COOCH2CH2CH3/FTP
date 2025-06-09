import javax.swing.*;
import java.awt.*;

class FTPClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("FTP Client - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton anonymousButton = new JButton("Anonymous");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(anonymousButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            JOptionPane.showMessageDialog(this, "Login: " + user);
            new MainDashboard(user);
            dispose();
        });

        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        anonymousButton.addActionListener(e -> {
            new MainDashboard("Anonymous");
            dispose();
        });
        setVisible(true);
    }
}

class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField otpField;

    public RegisterFrame() {
        setTitle("FTP Client - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("OTP:"));
        otpField = new JTextField();
        panel.add(otpField);

        JButton sendOtpButton = new JButton("Send OTP");
        JButton verifyButton = new JButton("Verify");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(sendOtpButton);
        buttonPanel.add(verifyButton);
        buttonPanel.add(backButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        sendOtpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "OTP sent to: " + emailField.getText());
        });

        verifyButton.addActionListener(e -> {
            String otp = otpField.getText();
            JOptionPane.showMessageDialog(this, "Verifying OTP: " + otp);
        });

        backButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }
}
