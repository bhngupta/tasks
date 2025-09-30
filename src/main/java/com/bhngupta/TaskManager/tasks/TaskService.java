package com.bhngupta.TaskManager.tasks;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repo;
    public TaskService(TaskRepository repo) { this.repo = repo; }
    public List<Task> findAll() { return repo.findAll(); }
    public Task save(Task t) { return repo.save(t); }
}