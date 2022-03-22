package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserService {

    User findOrCreate(Update update);

    User getUserByTelegramId(String telegramUserId);

    Boolean isAdministrator(Update update);

    User updateUser(User persistedUser);
}
