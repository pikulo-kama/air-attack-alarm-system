package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import com.adrabazha.air.attack.alarm.system.repository.UserRepository;
import com.adrabazha.air.attack.alarm.system.repository.UserStateRepository;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserStateRepository userStateRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(UserStateRepository userStateRepository, UserRepository userRepository) {
        this.userStateRepository = userStateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByTelegramUserId(String userId) {
        return userRepository.findByTelegramUserId(userId);
    }

    @Override
    public UserState getOrCreateState(String userId) {
        Optional<UserState> persistedState = userStateRepository.findById(userId);

        return persistedState.orElseGet(() -> userStateRepository.save(
                UserState.builder()
                        .userId(userId)
                        .currentHandler(MainMenuHandler.class.getName())
                        .build()));
    }

    @Override
    public UserState updateState(UserState userState) {
        userStateRepository.delete(userState);
        return userStateRepository.save(userState);
    }

    @Override
    public Boolean isAdministrator(String userId) {
        Optional<User> user = findByTelegramUserId(userId);
        return user.isPresent() && user.get().getIsAdmin();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
