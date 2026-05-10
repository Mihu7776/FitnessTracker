package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Compact user data returned by the e-mail search endpoint.
 *
 * @param id user identifier
 * @param email user's e-mail address
 */
public record UserEmailDto(@Nullable Long id, String email) {
}
