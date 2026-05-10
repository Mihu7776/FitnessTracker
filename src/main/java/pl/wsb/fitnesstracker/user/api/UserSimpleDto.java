package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Basic user data returned by the simple listing endpoint.
 *
 * @param id user identifier
 * @param firstName user's first name
 * @param lastName user's last name
 */
public record UserSimpleDto(@Nullable Long id, String firstName, String lastName) {
}
