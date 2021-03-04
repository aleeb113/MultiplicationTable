
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MultiplicationTableFrame extends JFrame {

    private JTextArea infoArea;
    private JLabel taskLabel;
    private JTextField resultField;
    private List<Task> tasks;
    private Task task;
    private Counter counter;
    private StringBuilder taskLog;
    private long timeStart;
    private Player player;
    private MTU mtu;

    public MultiplicationTableFrame() throws HeadlessException {
        taskLog = new StringBuilder();
        setLayout();
        getPlayer();
    }

    private void getPlayer() {
        mtu = new MTU();
        player = null;
        do player = mtu.getPlayer(this);
        while (player == null);
    }

    private void setLayout() {
        taskLabel = new JLabel(MTU.TASK_TXT, SwingConstants.CENTER);
        infoArea = new JTextArea(MTU.START_TXT);
        infoArea.setEditable(false);
        createResultField();
        JPanel mainPanel = getMainPanel();
        this.add(mainPanel);
        pack();
        repaint();
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton(MTU.START_BUTTON);
        buttonPanel.add(startButton);

        startButton.addActionListener((e) -> {
            counter = new Counter();
            taskLog.append(MTU.START_LOG_TXT);
            tasks = player.getTaskList();
            timeStart = System.currentTimeMillis();
            getNewTask();
            resetResultField();
        });
        return buttonPanel;
    }

    private void resetResultField() {
        resultField.setEditable(true);
        resultField.setText("");
        resultField.requestFocus();
    }

    private JPanel getMainPanel() {
        JPanel mainPanel = new JPanel();
        JLabel titleLabel = new JLabel(MTU.TITLE_TXT, SwingConstants.CENTER);

        mainPanel.setPreferredSize(new Dimension(500, 500));
        mainPanel.setLayout(new GridLayout(4, 0));
        mainPanel.add(titleLabel);

        JPanel buttonPanel = getButtonPanel();
        JPanel centerPanel = new JPanel();

        centerPanel.add(taskLabel);
        centerPanel.add(resultField);

        mainPanel.add(centerPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(new JScrollPane(infoArea));
        return mainPanel;
    }

    ActionListener resultActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            boolean correctAnswer = false;
            long taskTime = 0;
            if (resultField.isEditable()) {
                int inputResult = Integer.parseInt(resultField.getText());
                resultField.setText("");

                if (inputResult == task.getResult()) {
                    correctAnswer = true;
                    taskTime = handleCorrectAnswer(inputResult);
                } else handleWrongAnswer(inputResult);

                task.setScoreForTask(player,taskTime);

                if (correctAnswer) {
                    if (counter.getTaskCounter() < MTU.NUMBER_OF_TASK) {
                        getNewTask();
                    }
                    else handleEndOfRound();
                }
                infoArea.setText(taskLog.toString());
            }
        }

        private void handleWrongAnswer(int inputResult) {
            timeStart -= MultiplicationTableUtility.EXTRA_TIME;
            createWrongAnswerInfoInLog(inputResult);
            counter.increment(Counters.WRONG_ANSWERS);
        }

        private void handleEndOfRound() {
            resultField.setEditable(false);
            taskLog.append(MTU.END_TXT)
                    .append(MTU.SCORE_TXT)
                    .append(counter.getScore());
            mtu.savePlayersData(player);
        }

        private long handleCorrectAnswer(int inputResult) {
            long taskTime;
            taskTime = (System.currentTimeMillis() - timeStart) / 1000;
            createCorrectAnswerInfoInLog(inputResult, taskTime);
            counter.increment(Counters.GOODANSWERS);
            return taskTime;
        }
    };



    private void createWrongAnswerInfoInLog(int inputResult) {
        taskLog.append(MTU.WRONG_ANSWER1_TXT)
                .append(task.toString())
                .append(inputResult)
                .append(MTU.WRONG_ANSWER2_TXT);
    }

    private void createCorrectAnswerInfoInLog(int result, long taskTime) {
        taskLog.append(MTU.BRAVO_TXT)
                .append(task.toString())
                .append(result)
                .append(System.lineSeparator())
                .append("czas: ")
                .append(taskTime)
                .append(" sek.")
                .append(System.lineSeparator());
    }

    private void createResultField() {
        resultField = new JTextField(8);
        resultField.setEditable(false);
        resultField.setText("rozwiązanie");
        resultField.addActionListener(resultActionListener);
    }

    private void getNewTask() {
        timeStart = System.currentTimeMillis();
        counter.increment(Counters.TASKS);
        //task = MultiplicationTableUtility.getRandomTask(counter.getTaskCounter());
        int nextTaskIndex = counter.getTaskCounter()-1;
        task = tasks.get(nextTaskIndex);
        taskLabel.setText(task.getTask(counter.getTaskCounter()));
        taskLog.append(System.lineSeparator())
                .append(task.getTask(counter.getTaskCounter()));
        infoArea.setText(taskLog.toString());
    }
}
