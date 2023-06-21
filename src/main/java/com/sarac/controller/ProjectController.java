package com.sarac.controller;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.ResponseWrapper;
import com.sarac.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name="Project Controller",description = "Project API")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Get projects")
    public ResponseEntity<ResponseWrapper>getProjects(){
        List<ProjectDTO>projectDTOS=projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper(
                "Projects are successfully retrieved",projectDTOS, HttpStatus.OK
        ));
    }
    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get project by code")
    public ResponseEntity<ResponseWrapper>getProjectByCode(@PathVariable("projectCode")String projectCode){
        ProjectDTO projectDTO=projectService.getByProjectCode(projectCode);
        return ResponseEntity.ok(new ResponseWrapper(
                "Project is successfully retrieved",projectDTO,HttpStatus.OK
        ));
    }
    @PostMapping
    @RolesAllowed({"Admin","Manager"})
    @Operation(summary = "Create project")
    public ResponseEntity<ResponseWrapper>createProject(@RequestBody ProjectDTO dto){
        projectService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
                "Project is successfully created",HttpStatus.CREATED
        ));
    }
    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update project")
    public ResponseEntity<ResponseWrapper>updateProject(@RequestBody ProjectDTO dto){
        projectService.update(dto);
        return ResponseEntity.ok(new ResponseWrapper(
                "Project is successfully updated",HttpStatus.OK
        ));
    }
    @DeleteMapping("/{code}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete project")
    public ResponseEntity<ResponseWrapper>deleteProject(@PathVariable("code")String code){
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper(
                "Project is successfully deleted",HttpStatus.OK
        ));
    }
    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    @Operation(summary = "Get project by manager")
    public ResponseEntity<ResponseWrapper>getProjectByManager(){
       List<ProjectDTO>projectDTOList=projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper(
                "Porject are successfully retrieved",projectDTOList,HttpStatus.OK
        ));
    }
    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get completed project")
    public ResponseEntity<ResponseWrapper>managerCompleteProject(@PathVariable("projectCode")String projectCode){

        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper(
                "Project are successfully completed",HttpStatus.OK
        ));
    }
}
