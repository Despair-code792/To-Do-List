package org.project;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    private int id;
    private String desc;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    public Task(int id, String desc) {
        this.id = id;
        this.desc = desc;
        this.status = "todo";
        createdAt = LocalDateTime.now().withSecond(0).withNano(0);
        updatedAt = LocalDateTime.now().withSecond(0).withNano(0);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task)obj;
        return id == task.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Task id : " + id +
                "\nTask description : " + desc +
                "\nTask status : " + status +
                "\nTask created at : " + createdAt.format(formatter) +
                "\nTask updated at : " + updatedAt.format(formatter);
    }


}
