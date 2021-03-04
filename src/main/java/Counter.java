public class Counter {
    private int taskCounter;
    private int goodAnswersCounter;
    private int wrongAnswersCounter;

    public Counter() {
        taskCounter = 0;
        goodAnswersCounter = 0;
        wrongAnswersCounter = 0;
    }

    public void increment(Counters counter) {
        if (counter.equals(Counters.TASKS)) taskCounter++;
        else if (counter.equals(Counters.GOODANSWERS)) goodAnswersCounter++;
        else if (counter.equals(Counters.WRONG_ANSWERS)) wrongAnswersCounter++;
    }

    public int getTaskCounter() {
        return taskCounter;
    }

    public int getCorrectAnswersCounter() {
        return goodAnswersCounter;
    }

    public String getScore() {
        double score = (double) (taskCounter - wrongAnswersCounter) / (double) taskCounter;
        String mark = "";
        if (score == 1) mark = "szóstka 6, brawo!";
        else if (score > 0.85) mark = " piątka 5, bardzo dobrze!";
        else if (score > 0.75) mark = " czwórka 4, dobra robota";
        else if (score > 0.5) mark = " trója 3, stać Cię na więcej";
        else if (score > 0.35) mark = " dwója 2, to jakiś żart?";
        else mark = "jedynka! 1, popraw to natychmiast! ";
        return mark;
    }
}
