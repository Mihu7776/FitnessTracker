package pl.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found,
     * then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return optional user
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     *
     * @param email email of user
     * @return optional user
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return list of users
     */
    List<User> findAllUsers();

    /**
     * Retrieves users older than given date.
     *
     * @param date limit date
     * @return list of users
     */
    List<User> findUsersOlderThan(String date);
}