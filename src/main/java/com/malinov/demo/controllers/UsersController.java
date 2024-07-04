package com.malinov.demo.controllers;

import com.malinov.demo.dto.UsersResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.models.Users;
import com.malinov.demo.services.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponse save(@RequestBody Users users) {
        return usersService.save(users);
    }

    @GetMapping("/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponse> findByState(@PathVariable State state) {
        return usersService.findByState(state);
    }

    @GetMapping("/state-not/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponse> findByStateNot(@PathVariable State state) {
        return usersService.findByStateNot(state);
    }

    @GetMapping("/{id}/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse findByIdAndState(@PathVariable Long id, @PathVariable State state) {
        return usersService.findByIdAndState(id, state);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse update(@RequestBody Users users) {
        return usersService.update(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        usersService.delete(id);
    }

    @PutMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse activate(@RequestBody Users users) {
        return usersService.activate(users);
    }
}
