package org.project;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.nio.file.Paths;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class TaskManager {

    private List<Task> tasks;
    private int nextId = 1;
    private final String taskFile = "tasks.json";

    public TaskManager(){
        tasks = loadTasks();
    }

    private List<Task> loadTasks(){
        try {
            if (!Files.exists(Paths.get(taskFile))) {
                return new ArrayList<>();
            }
            byte[] jsonData = Files.readAllBytes(Paths.get(taskFile));
            if (jsonData.length == 0) {
                return new ArrayList<>();
            }
            Gson gson = createGson();
            Type listType = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(new String(jsonData), listType);
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("An error occurred while loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.toString());
                    }
                })
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDateTime.parse(json.getAsString());
                    }
                })
                .create();
    }

    public void saveTasks(){
        try (FileWriter writer = new FileWriter(taskFile)){
            Gson gson = createGson();
            gson.toJson(tasks,writer);
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks: "+e.getMessage());
        }
    }

    public void addTask(Task task){
        if(taskFoundById(task.getId())){
            System.out.println( "A task with the id provided already exists or the task already exists!");
        }else{
            tasks.add(task);
            saveTasks();
            System.out.println("Task added!");
        }

    }

    public int getNextId(){
        return nextId++;
    }

    public void updateTask(int id,String newDesc){
        Task taskToUpdate = tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);

        if(taskToUpdate != null){
            taskToUpdate.setDesc(newDesc);
            taskToUpdate.setUpdatedAt(LocalDateTime.now().withSecond(0).withNano(0));
            saveTasks();
        } else {
            System.out.println("Task not found!");

        }
    }

    public boolean taskFoundById(int id){
        for(Task task : tasks){
            if(task.getId() == id){
                return true;
            }
        }

        return false;
    }

    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }


    public void deleteTask(int id){
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            saveTasks();
            System.out.println("Task " + id + " deleted.");
        } else {
            System.out.println("Task not found!");
        }
    }

    public void markInProgress(int id){
        Task task = findTaskById(id);
        if(task == null){
            System.out.println("Task not found!");
        }else{
            task.setStatus("in-progress");
            saveTasks();
            System.out.println("Task is now in progress");
        }
    }

    public void markDone(int id){
        Task task = findTaskById(id);
        if(task == null){
            System.out.println("Task not found!");
        } else {
            task.setStatus("done");
            saveTasks();
            System.out.println("Task " + id + " marked as done.");
        }
    }


    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    public void listTasksByStatus(String status) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks with status: " + status);
        }
    }

}
