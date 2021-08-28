package io.torder.exam.controller.user;

import io.torder.exam.controller.common.ApiResponse;
import io.torder.exam.controller.user.dto.JoinRequest;
import io.torder.exam.controller.user.dto.JoinResponse;
import io.torder.exam.controller.user.dto.ModRequest;
import io.torder.exam.controller.user.dto.ModResponse;
import io.torder.exam.model.user.User;
import io.torder.exam.security.dto.JwtAuthentication;
import io.torder.exam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;

    @Async
    @PostMapping("join")
    public CompletableFuture<ApiResponse<JoinResponse>> join(@RequestBody JoinRequest joinRequest) {
        User newUser =  userService.join(joinRequest);
        JoinResponse joinResponse = new JoinResponse(newUser.getUserId(), true);
        return CompletableFuture.completedFuture(ApiResponse.OK(joinResponse));
    }

    @Async
    @PostMapping("mod-info")
    public CompletableFuture<ApiResponse<ModResponse>> modInfo(@AuthenticationPrincipal JwtAuthentication auth,
                @RequestBody ModRequest modRequest) {
        if (modRequest.getId().equals(auth.getUserId()))
            throw new AccessDeniedException("Cannot access other user's info!");

        User modUser = userService.modify(auth.getUserId(), modRequest);
        ModResponse modResponse = new ModResponse(modUser.getUserId(), true);
        return CompletableFuture.completedFuture(ApiResponse.OK(modResponse));
    }
}
