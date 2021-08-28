package io.torder.exam.service.user.dao;

import io.torder.exam.model.user.User;
import io.torder.exam.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDaoService {

    private final UserRepository userRepository;

    public boolean checkDupByUserId(String userId) {
        if (userRepository.findByUserId(userId).isPresent())
            return true;
        else
            return false;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User ID not found"));
    }
}
