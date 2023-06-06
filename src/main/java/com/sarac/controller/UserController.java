package com.sarac.controller;

import com.sarac.dto.ResponseWrapper;
import com.sarac.dto.UserDTO;
import com.sarac.exception.TicketingProjectException;
import com.sarac.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name="User Controller",description = "User API")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "Get users")
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper(
                "Users are successfully retrieved", userDTOList, HttpStatus.OK
        ));
    }
    @GetMapping("/{name}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get user by username ")
    public ResponseEntity<ResponseWrapper>getUserByUsername(@PathVariable("name")String name){
        UserDTO dto=userService.findByUserName(name);
        return ResponseEntity.ok(new ResponseWrapper(
                "User is successfully retrieved", dto, HttpStatus.OK
        ));
    }
    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create user")
    public ResponseEntity<ResponseWrapper>createUser(@RequestBody UserDTO dto){

        userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
              "User is successfully created",HttpStatus.CREATED
        ));
    }
    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper>UpdateUser(@RequestBody UserDTO dto){
        userService.update(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
                "User is successfully updated",HttpStatus.CREATED
        ));

    }
    @DeleteMapping ("/{name}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete user")
    public ResponseEntity<ResponseWrapper>DeleteUser(@PathVariable("name")String name) throws TicketingProjectException {
        userService.delete(name);
        return ResponseEntity.ok(new ResponseWrapper(
                "User is Deleted", HttpStatus.OK
        ));
    }

}