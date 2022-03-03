package com.adrabazha.air.attack.alarm.system.telegram.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class TelegramDistrictServiceImpl implements TelegramDistrictService {

    private final DistrictStateService districtStateService;

    public TelegramDistrictServiceImpl(DistrictStateService districtStateService) {
        this.districtStateService = districtStateService;
    }

    @Override
    public SendMessageWrapper buildDistrictStateResponse(String chatId, String input) {
        List<DistrictState> matchingDistricts = districtStateService.findAllStates().stream()
                .filter(district -> partiallyMatches(input, district))
                .collect(Collectors.toList());

        if (matchingDistricts.size() == 1) {
            return buildConcreteDistrictResponse(chatId, matchingDistricts.get(0));
        } else if (matchingDistricts.isEmpty()) {
            return buildHelpResponse(chatId);
        }

        return buildResponseWithKeyboard(chatId, matchingDistricts);
    }

    private SendMessageWrapper buildConcreteDistrictResponse(String chatId, DistrictState districtState) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(chatId);
        wrapper.setText(districtState.getStateMessage());

        InlineKeyboardButton button = new InlineKeyboardButton(districtState.getAlarmState().getAlarmButtonContent());
        button.setCallbackData(districtState.buildCallbackData());
        wrapper.addInlineButton(button);

        return wrapper;
    }

    private SendMessageWrapper buildHelpResponse(String chatId) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setText("\uD83E\uDD14 За вашим запитом нічого не знайдено\nСпробуйте ще раз");
        wrapper.setChatId(chatId);
        return wrapper;
    }

    private SendMessageWrapper buildResponseWithKeyboard(String chatId, List<DistrictState> districts) {
        SendMessageWrapper wrapper = new SendMessageWrapper();

        StringBuilder builder = new StringBuilder();
        builder.append("\uD83E\uDD14 Який саме обласний центр вас цікавить?\n\n");
        districts.forEach(district -> builder.append(format("%s %s%n", district.getAlarmState().getEmoji(), district.getDistrictName())));

        wrapper.setText(builder.toString());
        wrapper.setChatId(chatId);
        return wrapper;
    }

    private Boolean partiallyMatches(String input, DistrictState district) {
        return district.getDistrictName().toLowerCase().contains(input.toLowerCase());
    }
}
