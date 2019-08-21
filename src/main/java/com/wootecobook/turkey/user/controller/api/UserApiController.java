package com.wootecobook.turkey.user.controller.api;

import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.user.service.UserService;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserResponseById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.save(userRequest);
        URI uri = linkTo(UserApiController.class).slash(userResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> list(UserSession userSession) {
        return ResponseEntity.ok(userService.findAllUsersWithoutCurrentUser(userSession.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, UserSession userSession) {
        userService.delete(id, userSession.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}/search")
    public ResponseEntity<List<UserResponse>> search(@PathVariable String name) {
        List<UserResponse> userResponses = userService.findByName(name);
        return ResponseEntity.ok(userResponses);
    }

}