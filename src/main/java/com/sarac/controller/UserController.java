package com.sarac.controller;

import com.sarac.dto.ResponseWrapper;
import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper(
                "Users are successfully retrieved", userDTOList, HttpStatus.OK
        ));
    }
    @GetMapping("/{name}")
    public ResponseEntity<ResponseWrapper>getUserByUsername(@PathVariable("name")String name){
        UserDTO dto=userService.findByUserName(name);
        return ResponseEntity.ok(new ResponseWrapper(
                "User is successfully retrieved", dto, HttpStatus.OK
        ));
    }
    @PostMapping
    public ResponseEntity<ResponseWrapper>createUser(@RequestBody UserDTO dto){

        userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
              "User is successfully created",HttpStatus.CREATED
        ));
    }
    @PutMapping
    public ResponseEntity<ResponseWrapper>UpdateUser(@RequestBody UserDTO dto){
        userService.update(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(
                "User is successfully updated",HttpStatus.CREATED
        ));

    }
    @DeleteMapping ("/{name}")
    public ResponseEntity<ResponseWrapper>DeleteUser(@PathVariable("name")String name){
        userService.delete(name);
        return ResponseEntity.ok(new ResponseWrapper(
                "User is Deleted", HttpStatus.OK
        ));
    }

}