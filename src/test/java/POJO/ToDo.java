package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDo {
    private int userId;
    private int id;
    private String unvan;
    private boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUnvan() {
        return unvan;
    }
    @JsonProperty("title")
    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "userId=" + userId +
                ", id=" + id +
                ", unvan='" + unvan + '\'' +
                ", completed=" + completed +
                '}';
    }
}
