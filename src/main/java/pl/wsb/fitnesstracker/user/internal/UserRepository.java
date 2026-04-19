package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return Optional containing found user or Optional.empty() if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Native SQL (Zadanie 4): users by email domain, e.g. "@gmail.com"
     */
    @Query(
            value = "SELECT * FROM users u WHERE u.email LIKE CONCAT('%', :domain)",
            nativeQuery = true
    )
    List<User> findByEmailDomain(@Param("domain") String domain);
}