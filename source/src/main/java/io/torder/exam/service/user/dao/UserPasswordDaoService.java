package io.torder.exam.service.user.dao;

import io.torder.exam.model.user.User;
import io.torder.exam.model.user.UserPassword;
import io.torder.exam.repository.user.UserPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserPasswordDaoService {

    private final UserPasswordRepository userPasswordRepository;

    public void saveUserPassword(UserPassword userPassword) {
        userPasswordRepository.saveAndFlush(userPassword);
    }

    public List<UserPassword> findAll(User user) {
        return userPasswordRepository.findAllByUser(user);
    }
}
