package io.torder.exam.service.user;

import io.torder.exam.controller.user.dto.JoinRequest;
import io.torder.exam.controller.user.dto.ModRequest;
import io.torder.exam.model.user.Role;
import io.torder.exam.model.user.User;
import io.torder.exam.model.user.UserPassword;
import io.torder.exam.service.user.dao.UserDaoService;
import io.torder.exam.service.user.dao.UserPasswordDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDaoService userDaoService;
    private final UserPasswordDaoService userPasswordDaoService;

    /**
     * 신규 사용자 회원가입
     */
    @Transactional
    public User join(JoinRequest joinRequest) {
        //영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한
        if (joinRequest.getId() == null || !joinRequest.getId().matches("^[a-z][a-z|_|\\\\-|0-9]{4,19}$")) {
            throw new IllegalArgumentException("invalid user ID [" + joinRequest.getId() + "]");
        }

        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (joinRequest.getPw() == null || !joinRequest.getPw().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            throw new IllegalArgumentException("invalid user password");
        }

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (joinRequest.getPwConfirm() == null || !joinRequest.getPw().equals(joinRequest.getPwConfirm())) {
            throw new IllegalArgumentException("mismatched password confirm");
        }

        //중복된 아이디 제한
        if (userDaoService.checkDupByUserId(joinRequest.getId()))
            throw new IllegalArgumentException("duplicated user ID ["+ joinRequest.getId()+"]");

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(joinRequest.getPw());

        User newUser = User.builder()
                .userId(joinRequest.getId())
                .role(Role.USER)
                .build();
        newUser = userDaoService.saveUser(newUser);

        UserPassword userPassword = UserPassword.builder()
                .user(newUser)
                .password(encodedPassword)
                .build();
        userPasswordDaoService.saveUserPassword(userPassword);

        return newUser;
    }

    /**
     * 사용자 정보수정 (현재는 비밀번호만 수정)
     */
    @Transactional
    public User modify(String userId, ModRequest modRequest) {
        //기존 패스워드 검증
        User user = validateUser(userId, modRequest.getCurrentPw());
        
        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (modRequest.getPw() == null || !modRequest.getPw().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            throw new IllegalArgumentException("invalid user password");
        }

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (modRequest.getPwConfirm() == null || !modRequest.getPw().equals(modRequest.getPwConfirm())) {
            throw new IllegalArgumentException("mismatched password confirm");
        }

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(modRequest.getPw());

        //이전에 사용했던 비밀번호는 다시 설정할 수 없도록 설정
        List<UserPassword> userPasswords = userPasswordDaoService.findAll(user);
        for (UserPassword up : userPasswords) {
            if (pe.matches(modRequest.getPw().trim(), up.getPassword()))
                throw new IllegalArgumentException("user password that have been used before");
        }

        UserPassword newUserPassword = UserPassword.builder()
                .user(user)
                .password(encodedPassword)
                .build();
        userPasswordDaoService.saveUserPassword(newUserPassword);
        userDaoService.saveUser(user.updateModdate());

        return user;
    }

    /**
     * 사용자 아이디와 비밀번호로 검증을 진행
     * @return 성공시 User 리턴 / 실패시 IllegalArgumentException 발생
     */
    public User validateUser(String userId, String password) {
        //userId를 사용하여 비밀번호 내역 조회
        User user = userDaoService.findUser(userId);
        List<UserPassword> userPasswords = userPasswordDaoService.findAll(user);
        UserPassword curUserPassword = userPasswords.get(0);

        //비밀번호 해시값 검증
        PasswordEncoder pe = new BCryptPasswordEncoder();
        if (!pe.matches(password, curUserPassword.getPassword()))
            throw new IllegalArgumentException("user password not matched");
        
        return user;
    }
}
