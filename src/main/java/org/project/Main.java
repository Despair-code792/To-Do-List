package org.project;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read user input (command)
            System.out.print("Enter a command: ");
            String commandLine = scanner.nextLine();
            String[] commandArgs = commandLine.split(" ");

            if (commandArgs.length <= 0) {
                System.out.println("No command provided. Please provide a valid command.");
                continue;
            }

            String command = commandArgs[0].toLowerCase();

            switch (command) {

                case "add":
                    if (commandArgs.length < 2) {
                        System.out.println("Please provide a valid task description!");
                    } else {
                        String desc = String.join(" ", java.util.Arrays.copyOfRange(commandArgs, 1, commandArgs.length));
                        Task task = new Task(taskManager.getNextId(), desc);
                        taskManager.addTask(task);
                        System.out.println("Task added successfully (ID: " + task.getId() + ")");
                    }
                    break;

                case "update":
                    if (commandArgs.length < 3) {
                        System.out.println("Please provide a valid id and description: ");
                    } else {
                        int id = Integer.parseInt(commandArgs[1]);
                        String newDesc = String.join(" ", java.util.Arrays.copyOfRange(commandArgs, 2, commandArgs.length));
                        taskManager.updateTask(id, newDesc);
                        System.out.println("Task " + id + " updated successfully.");
                    }
                    break;

                case "delete":
                    if (commandArgs.length < 2) {
                        System.out.println("Please provide a valid id: ");
                    } else {
                        int id = Integer.parseInt(commandArgs[1]);
                        taskManager.deleteTask(id);
                    }
                    break;

                case "mark-in-progress":
                    if (commandArgs.length < 2) {
                        System.out.println("Please provide a valid id: ");
                    } else {
                        int id = Integer.parseInt(commandArgs[1]);
                        taskManager.markInProgress(id);
                    }
                    break;

                case "mark-done":
                    if (commandArgs.length < 2) {
                        System.out.println("Please provide task ID.");
                    } else {
                        int id = Integer.parseInt(commandArgs[1]);
                        taskManager.markDone(id);
                        System.out.println("Task " + id + " marked as done.");
                    }
                    break;

                case "list":
                    if (commandArgs.length == 1) {
                        taskManager.listAllTasks();
                    } else {
                        String status = commandArgs[1].toLowerCase();
                        taskManager.listTasksByStatus(status);
                    }
                    break;

                case "quit":
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command. Available commands: add, update, delete, mark-in-progress, mark-done, list, quit");
                    break;
            }
        }
    }
}
