import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class Player implements Serializable {

    private String name;
    private Map<Task, Integer> scoreTable;

    public Player(String name) {
        this.name = name;
        this.scoreTable = createNewScoreTable();
    }

    private Map<Task, Integer> createNewScoreTable() {
        Map<Task, Integer> scoreTable = new LinkedHashMap<>();
        List<Task> taskList = Task.getNewTaskList();
        Integer initialValue = 0;
        for (Task task : taskList) scoreTable.put(task, initialValue);
        return scoreTable;
    }

    public Map<Task, Integer> getScoreTable() {
        return scoreTable;
    }

    @Override
    public String toString() {
        return name;
    }

    public void printScoreTable() {
        for (Map.Entry<Task, Integer> entry : scoreTable.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    public List<Task> getTaskList() {
        List<Task> tasks = new ArrayList<>();

        Consumer<Map.Entry<Task, Integer>> addTaskIfScoreIsZero = entry -> {
            if (entry.getValue() == 0) tasks.add(entry.getKey());
        };

        Consumer<Map.Entry<Task, Integer>> addTaskIfScoreIsNotZero = entry -> {
            if (entry.getValue() != 0) tasks.add(entry.getKey());
        };

        scoreTable.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(addTaskIfScoreIsZero);

        if (tasks.size() < MTU.NUMBER_OF_TASK) {
            scoreTable.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(addTaskIfScoreIsNotZero);
        }
        return tasks;
    }

    public void printSortedScoreTable() {
        scoreTable.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }
}
