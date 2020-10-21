package Model;


public class Task {

    private int id;
    private String task;
    private String description;
    private String date;
    private String time;
    private String priority;

    public Task(int id, String task, String description, String date, String time, String priority) {
        this.id = id;
        this.task = task;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public Task(String task, String date, String time, String priority) {
        this.task = task;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public Task(String task, String description, String date, String time, String priority) {
        this.task = task;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
