package tobbit.movieproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.Group;
import tobbit.movieproject.model.UserEvent;
import tobbit.movieproject.service.CalendarService;
import tobbit.movieproject.service.GroupService;
import tobbit.movieproject.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final CalendarService calendarService;
    private final GroupService groupService;

    @Autowired
    public UserController(UserService userService, CalendarService calendarService, GroupService groupService) {
        this.userService = userService;
        this.calendarService = calendarService;
        this.groupService = groupService;
    }

    @GetMapping("/api/allUsers")
    public ResponseEntity<List<String>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/api/createGroup")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.createGroup(group.getName(), group.getCreatedBy(), group.getDescription(), group.getMembers()));
    }

    @GetMapping("/api/userGroup")
    public ResponseEntity<List<Group>> userGroup(@RequestParam String currentUser) {
        return ResponseEntity.ok(groupService.getUserGroup(currentUser));
    }

}
