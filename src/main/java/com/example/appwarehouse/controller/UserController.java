package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.User;
import com.example.appwarehouse.entity.WareHouse;
import com.example.appwarehouse.service.UserService;
import com.example.appwarehouse.transfer.Result;
import com.example.appwarehouse.transfer.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping
    public Result addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public Result editUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return userService.editUser(id, userDTO);
    }

}
