package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {

        log.info("Creating User {}", user);

        if (user.getId() != null) {
            throw new IllegalArgumentException(
                    "User has already DB ID, update is not permitted!"
            );
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User with id " + id + " not found"
                        )
                );

        user.update(
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getBirthdate(),
                updatedUser.getEmail()
        );

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUser(final Long userId) {

        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    public List<User> findUsersOlderThan(String date) {

        LocalDate localDate = LocalDate.parse(date);

        return userRepository.findByBirthdateBefore(localDate);
    }
}