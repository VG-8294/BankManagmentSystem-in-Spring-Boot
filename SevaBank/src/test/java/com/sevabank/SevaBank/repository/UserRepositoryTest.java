package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    private User createUser(
            String name,
            String email,
            int age
    ) {

        User user = new User(
                name,
                email,
                "Password@123",
                age
        );

        return userRepository.save(user);
    }


    // =========================================================
    // findByEmail
    // =========================================================

    @Test
    void shouldFindUserByEmail() {

        User savedUser = createUser(
                "Vishal",
                "vishal.repository@gmail.com",
                22
        );

        Optional<User> result =
                userRepository.findByEmail(
                        "vishal.repository@gmail.com"
                );

        assertTrue(result.isPresent());

        User user = result.get();

        assertEquals(
                savedUser.getId(),
                user.getId()
        );

        assertEquals(
                "Vishal",
                user.getName()
        );

        assertEquals(
                "vishal.repository@gmail.com",
                user.getEmail()
        );
    }


    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {

        Optional<User> result =
                userRepository.findByEmail(
                        "doesnotexist123@gmail.com"
                );

        assertFalse(result.isPresent());
    }


    // =========================================================
    // findByAgeGreaterThan
    // =========================================================

    @Test
    void shouldFindUsersWithAgeGreaterThan() {

        createUser(
                "YoungUser",
                "young.repository@gmail.com",
                20
        );

        createUser(
                "OlderUser",
                "older.repository@gmail.com",
                30
        );

        List<User> result =
                userRepository.findByAgeGreaterThan(25);

        assertNotNull(result);

        assertTrue(
                result.stream()
                        .anyMatch(user ->
                                user.getEmail()
                                        .equals("older.repository@gmail.com")
                        )
        );

        for (User user : result) {
            assertTrue(user.getAge() > 25);
        }
    }


    @Test
    void shouldReturnEmptyWhenNoUserIsOlderThanGivenAge() {

        createUser(
                "YoungUser",
                "young.only.repository@gmail.com",
                20
        );

        createUser(
                "AnotherYoungUser",
                "another.young.repository@gmail.com",
                21
        );

        List<User> result =
                userRepository.findByAgeGreaterThan(999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    // =========================================================
    // findByAgeGreaterThanEqual
    // =========================================================

    @Test
    void shouldFindUsersWithAgeGreaterThanOrEqual() {

        createUser(
                "TwentyTwo",
                "twentytwo.repository@gmail.com",
                22
        );

        createUser(
                "Thirty",
                "thirty.repository@gmail.com",
                30
        );

        List<User> result =
                userRepository.findByAgeGreaterThanEqual(22);

        assertNotNull(result);

        assertTrue(
                result.stream()
                        .anyMatch(user ->
                                user.getEmail()
                                        .equals("twentytwo.repository@gmail.com")
                        )
        );

        assertTrue(
                result.stream()
                        .anyMatch(user ->
                                user.getEmail()
                                        .equals("thirty.repository@gmail.com")
                        )
        );

        for (User user : result) {
            assertTrue(user.getAge() >= 22);
        }
    }


    @Test
    void shouldReturnEmptyWhenNoUserMeetsMinimumAge() {

        createUser(
                "YoungUser",
                "young.minimum.repository@gmail.com",
                18
        );

        createUser(
                "AnotherYoungUser",
                "another.minimum.repository@gmail.com",
                20
        );

        List<User> result =
                userRepository.findByAgeGreaterThanEqual(100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}