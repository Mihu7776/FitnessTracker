package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return Optional containing found user or Optional.empty() if none matched
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds users whose e-mail contains the provided text, ignoring case.
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Native SQL: users by email domain, e.g. "@gmail.com"
     */
    @Query(
            value = "SELECT * FROM users u WHERE u.email LIKE CONCAT('%', :domain)",
            nativeQuery = true
    )
    List<User> findByEmailDomain(@Param("domain") String domain);

    /**
     * Finds users born before given date.
     */
    List<User> findByBirthdateBefore(LocalDate date);
}
