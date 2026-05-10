package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto dto) {

        User user = new User(
                dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email()
        );

        userService.createUser(user);
    }

    @GetMapping
    public List<UserDto> getAll() {

        return userProvider.findAllUsers()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<UserDto> getSimple() {

        return userProvider.findAllUsers()
                .stream()
                .map(userMapper::toUserSimpleDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {

        return userProvider.getUser(id)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }

    @GetMapping("/email")
    public List<UserDto> getByEmail(@RequestParam String email) {

        return userProvider.findAllUsers()
                .stream()
                .filter(user ->
                        user.getEmail().toLowerCase()
                                .contains(email.toLowerCase()))
                .map(userMapper::toUserEmailDto)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getOlderThan(@PathVariable String time) {

        LocalDate date = LocalDate.parse(time);

        return userProvider.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .map(userMapper::toUserDto)
                .toList();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {

        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public void update(
            @PathVariable Long userId,
            @RequestBody UserDto dto
    ) {

        User user = new User(
                dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email()
        );

        userService.updateUser(userId, user);
    }
}