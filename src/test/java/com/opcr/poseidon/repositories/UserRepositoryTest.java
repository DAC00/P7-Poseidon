package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFullname("fullName");
        user.setUsername("username");
        user.setRole("USER");
        user.setPassword("pass@1Aa");
    }

    @Test
    public void saveBidListTest() {
        userRepository.save(user);
        assertTrue(userRepository.findAll().contains(user));
        assertEquals(userRepository.findAll().size(), 3);
    }

    @Test
    public void updateBidListTest() {
        userRepository.save(user);

        User update = userRepository.findById(user.getId()).orElseThrow();
        update.setFullname("TEST");
        userRepository.save(update);

        User toVerify = userRepository.findById(update.getId()).orElseThrow();
        assertEquals(userRepository.findAll().size(), 3);
        assertEquals(toVerify.getFullname(), "TEST");
    }

    @Test
    public void deleteBidListTest() {
        userRepository.save(user);
        assertEquals(userRepository.findAll().size(), 3);
        userRepository.deleteById(user.getId());
        assertEquals(userRepository.findAll().size(), 2);
    }
}
