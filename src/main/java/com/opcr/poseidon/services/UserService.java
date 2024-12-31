package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.User;
import com.opcr.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all the users from de database.
     *
     * @return List of all the User.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Get the User with the id : userId.
     *
     * @param userId of the User.
     * @return User with the id : userId.
     */
    public Optional<User> getUser(Integer userId) {
        return userRepository.findById(userId);
    }

    /**
     * Save the User in the database after hashing the password with BCrypt.
     *
     * @param userToSave is a User with a plain password.
     */
    public void saveUser(User userToSave) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userToSave.setPassword(encoder.encode(userToSave.getPassword()));
        userRepository.save(userToSave);
    }

    /**
     * Update the User with id : userId.
     * Hash the password with BCrypt.
     *
     * @param userId      of the User to update.
     * @param userUpdated is the User with updated information.
     */
    public void updateUserById(Integer userId, User userUpdated) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userUpdated.setPassword(encoder.encode(userUpdated.getPassword()));
        userUpdated.setId(userId);
        userRepository.save(userUpdated);
    }

    /**
     * Delete the User with id : userId.
     *
     * @param userId of the User to delete.
     */
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
