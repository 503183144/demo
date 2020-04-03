package demo.controller;

import com.amazonaws.services.xray.model.Http;
import demo.domain.User;
import demo.serviceLayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping()
public class UserController {

    @Autowired
    Service service;

    @GetMapping(value = "/user")
    public List<User> getAllUsers() throws Exception {
        return service.findAllUsers();
    }

    @GetMapping(value = "/tenant")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUserByUserType(@RequestBody(required = true) String userType, @RequestBody(required = true) String tenant) throws Exception {

        return service.findAllUsersByUserType(userType, tenant);
    }

    @GetMapping(value = "/user/{userid}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable String userid) throws Exception {
        return service.findByUserId(userid);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUserInfo(@RequestBody User user) throws Exception {
        service.updateUser(user);
    }

    @DeleteMapping(value = "user/{userid}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable String userid) throws Exception {
        service.deleteUser(userid);
    }
}
