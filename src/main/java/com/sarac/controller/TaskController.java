package com.sarac.controller;

import com.sarac.dto.ResponseWrapper;
import com.sarac.dto.TaskDTO;
import com.sarac.enums.Status;
import com.sarac.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Controller",description = "Task API")
public class TaskController {

    @Autowired
    TaskService taskService;
    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Get tasks")
    public ResponseEntity<ResponseWrapper>getTasks(){
        List<TaskDTO>taskDTOList=taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper(
                "Tasks are successfully retrieved",taskDTOList, HttpStatus.OK
        ));
    }
    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get task by id")
    public ResponseEntity<ResponseWrapper>getTaskByID(@PathVariable("id")Long id){
        TaskDTO dto=taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper(
                "Task is successfully retrieved",dto, HttpStatus.OK
        ));
    }
    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Create task")
    public ResponseEntity<ResponseWrapper>createTask(@RequestBody TaskDTO dto){
        taskService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
                "Task is successfully created", HttpStatus.CREATED
        ));
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete task")
    public ResponseEntity<ResponseWrapper>deleteTask(@PathVariable("id")Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper(
                "Task is successfully deleted",HttpStatus.OK
        ));
    }
    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update task")
    public ResponseEntity<ResponseWrapper>updateTask(@RequestBody TaskDTO dto){
        taskService.update(dto);
        return ResponseEntity.ok(new ResponseWrapper(
                "Task is successfully updated", HttpStatus.OK
        ));
    }
    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    @Operation(summary = "Get pending task")
    public ResponseEntity<ResponseWrapper>EmployeePendingTasks(){
        List<TaskDTO>taskDTOList=taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper(
                "Tasks are successfully retrieved",taskDTOList,HttpStatus.OK
        ));
    }
    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    @Operation(summary = "Get Update task")
    public ResponseEntity<ResponseWrapper>EmployeeUpdateTasks(@RequestBody TaskDTO dto){
        taskService.update(dto);
        return ResponseEntity.ok(new ResponseWrapper(
                "Task is successfully updated",HttpStatus.OK
        ));
    }
    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    @Operation(summary = "Get archive task")
    public ResponseEntity<ResponseWrapper>EmployeeArchiveTask(){
        List<TaskDTO>taskDTOList=taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper(
                "Task are successfully retrieved",taskDTOList,HttpStatus.OK
        ));
    }
}
