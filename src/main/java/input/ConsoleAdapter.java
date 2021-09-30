package input;

import application.ProjectService;
import application.TaskService;
import application.UserService;
import domain.Project;
import domain.Task;
import domain.User;
import savelogic.JsonWriter;


import java.io.IOException;
import java.util.Scanner;

public class ConsoleAdapter {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    private final Scanner scanner;
    private final JsonWriter jsonWriter;


    public ConsoleAdapter(TaskService taskService, ProjectService projectService, UserService userService, JsonWriter jsonWriter
                          , Scanner scanner) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
        this.jsonWriter = jsonWriter;
        this.scanner = scanner;

    }

    {
        System.out.println("Press to start");
    }

    String commandNumber = null;

    public void startApp() throws IOException {
        showCommands();

        while (commandNumber != "0") {
            commandNumber = scanner.next().trim();

            switch (commandNumber) {

                case "1":
                    fillUser();
                    break;
                case "2":
                    System.out.println("==getAllUsers()===");
                    System.out.println(userService.getUsers());
                    break;
                case "3":
                    System.out.println("Insert your department:");
                    Integer insertedDepartment = scanner.nextInt();
                    User userByDepartment = userService.findUserByDepartment(insertedDepartment);
                    System.out.println(userByDepartment.toString());
                    System.out.println("Press 4 to get project menu or 0 to exit");
                    break;
                case "4":
                    doProject();
                    break;
                case "5":
                    fillInTask();
                    System.out.println("To add new task, don't forget to insert 5");
                    break;
                case "6":
                    takeAwayTask();
                    break;
                case "7":

                case "0":
                    return;

            }
        }
    }


    private void doProject() throws IOException {

        System.out.println("Insert  Project's number:");
        Integer projectKey = scanner.nextInt();
        System.out.println("Insert new Project's name:");
        String insertedProjectName = scanner.next();
        System.out.println("Insert  Project's description:");
        String projectDescription = scanner.next();
        projectService.createProject(projectKey, new Project(insertedProjectName, projectDescription));
        jsonWriter.writeProject();
        System.out.println(projectService.getAllProjects());
    }

    private void takeAwayTask() throws IOException {
        System.out.println("Delete task:");
        System.out.println(taskService.getTaskList());
        System.out.println("Task to be deleted:");
        String deleteTask = scanner.next();
        taskService.removeSelectedTask(deleteTask);
        jsonWriter.writeTask();
        System.out.println("AFTER REMOVE:\n" + taskService.getTaskList());
    }

    private void fillInTask() throws IOException {
        System.out.println("Task key:");
        Integer taskKey = scanner.nextInt();
        System.out.println("Task:");
        String taskName = scanner.next();
        System.out.println("Task Description:");
        String taskDescription = scanner.next();
        taskService.addTask(taskKey, new Task(taskName, taskDescription));
        jsonWriter.writeTask();
        System.out.println("Task List:\n" + taskService.getTaskList());
    }

    private void fillUser() throws IOException {
        System.out.println("=====Enter Department id====");
        Integer userKey = scanner.nextInt();
        System.out.println("=====Create new USer =====\nEnter your name:");
        String name = scanner.next().trim();
        System.out.println("\n=====Enter your surname:====");
        String surname = scanner.next().trim();
        userService.createNewUser(userKey, new User(name, surname));
        jsonWriter.writeUser();
        System.out.println("Insert command's code");
    }

    private void showCommands() {
        System.out.println("===Select an option:===\n" +
                "0. Finish programme\n" +
                "1. Create new User\n" +
                "2. Show Users\n" +
                "3. Select User\n" +
                "4. Create new project\n" +
                "5. Fill in Task list\n" +
                "6. Remove task from task list\n" +
                "0. Exit");
    }

}
