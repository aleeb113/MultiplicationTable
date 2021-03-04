import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task implements Serializable {
    private String task;
    private int result;

    public Task() {
        task = "";
        result = 0;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static List<Task> getNewTaskList() {
        List<Task> tasks = new ArrayList<>();
        for (int x = 2; x < 11; x++)
            for (int y = 2; y < 11; y++) {
                tasks.add(new MultiplicationTask(x, y));
                tasks.add(new DivisionTask(x, y));
            }
        Collections.shuffle(tasks);
        return tasks;
    }

     protected void setScoreForTask(Player player,long taskTime) {
        int score = 1;
        if (taskTime > 50) score += 50;
        else score += taskTime;
        player.getScoreTable().put(this, score);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return task;
    }

    public String getTask(int taskNumber) {
        return "Zadanie nr " + taskNumber + ":    " + task + "  \n";
    }
}
