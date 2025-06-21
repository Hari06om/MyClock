import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClock extends JFrame {

    // Clock
    JLabel clockLabel;

    // Timer
    JLabel timerLabel;
    JTextField timerInput;
    JButton startTimerButton;
    Timer countdownTimer;
    int remainingSeconds = 0;

    // Stopwatch
    JLabel stopwatchLabel;
    JButton startStopButton, resetButton;
    Timer stopwatchTimer;
    int stopwatchSeconds = 0;
    boolean running = false;

    public MyClock() {
        setTitle("MyClock - Clock | Timer | Stopwatch");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Clock", createClockPanel());
        tabs.add("Timer", createTimerPanel());
        tabs.add("Stopwatch", createStopwatchPanel());

        add(tabs);
        setVisible(true);

        runClock();
    }

    JPanel createClockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        clockLabel = new JLabel("", JLabel.CENTER);
        clockLabel.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(clockLabel, BorderLayout.CENTER);
        return panel;
    }

    void runClock() {
        Timer clockTimer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            clockLabel.setText(sdf.format(new Date()));
        });
        clockTimer.start();
    }

    JPanel createTimerPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        timerLabel = new JLabel("00:00", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        panel.add(timerLabel);

        timerInput = new JTextField();
        timerInput.setHorizontalAlignment(JTextField.CENTER);
        timerInput.setToolTipText("Enter seconds");
        panel.add(timerInput);

        startTimerButton = new JButton("Start Timer");
        startTimerButton.addActionListener(e -> startCountdown());
        panel.add(startTimerButton);

        return panel;
    }

    void startCountdown() {
        try {
            remainingSeconds = Integer.parseInt(timerInput.getText());
            if (remainingSeconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number of seconds.");
            return;
        }

        if (countdownTimer != null) countdownTimer.stop();

        countdownTimer = new Timer(1000, e -> {
            if (remainingSeconds > 0) {
                remainingSeconds--;
                timerLabel.setText(formatTime(remainingSeconds));
            } else {
                timerLabel.setText("00:00");
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "⏰ Time's up!");
            }
        });

        timerLabel.setText(formatTime(remainingSeconds));
        countdownTimer.start();
    }

    JPanel createStopwatchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        stopwatchLabel = new JLabel("00:00", JLabel.CENTER);
        stopwatchLabel.setFont(new Font("Arial", Font.BOLD, 36));
        panel.add(stopwatchLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startStopButton = new JButton("Start");
        resetButton = new JButton("Reset");

        startStopButton.addActionListener(e -> toggleStopwatch());
        resetButton.addActionListener(e -> resetStopwatch());

        buttonPanel.add(startStopButton);
        buttonPanel.add(resetButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        stopwatchTimer = new Timer(1000, e -> {
            stopwatchSeconds++;
            stopwatchLabel.setText(formatTime(stopwatchSeconds));
        });

        return panel;
    }

    void toggleStopwatch() {
        if (running) {
            stopwatchTimer.stop();
            startStopButton.setText("Start");
        } else {
            stopwatchTimer.start();
            startStopButton.setText("Stop");
        }
        running = !running;
    }

    void resetStopwatch() {
        stopwatchTimer.stop();
        stopwatchSeconds = 0;
        stopwatchLabel.setText("00:00");
        startStopButton.setText("Start");
        running = false;
    }

    String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void main(String[] args) {
        new MyClock();
    }
}
