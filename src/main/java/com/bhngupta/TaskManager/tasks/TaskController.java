package com.bhngupta.TaskManager.tasks;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public List<Task> getAll() { return service.findAll(); }

    @PostMapping
    public Task create(@RequestBody Task t) { return service.save(t); }
}