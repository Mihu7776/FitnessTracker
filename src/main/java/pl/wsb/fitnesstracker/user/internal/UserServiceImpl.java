package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
                .orElseThrow(() -> new UserNotFoundException(id));

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

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(final Long userId) {

        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(final String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByEmailContaining(String email) {

        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersOlderThan(String date) {

        LocalDate localDate = LocalDate.parse(date);

        return userRepository.findByBirthdateBefore(localDate);
    }
}
