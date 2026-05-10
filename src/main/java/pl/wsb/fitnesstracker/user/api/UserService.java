package pl.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction,
 * whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user The user to be created
     * @return The created user
     */
    User createUser(User user);

    /**
     * Updates an existing user.
     *
     * @param id   The id of the user to be updated
     * @param user The user data to be applied
     * @return The updated user
     */
    User updateUser(Long id, User user);

    /**
     * Deletes user by id.
     *
     * @param id user id
     */
    void deleteUser(Long id);
}