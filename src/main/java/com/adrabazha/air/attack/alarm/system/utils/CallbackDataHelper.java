package com.adrabazha.air.attack.alarm.system.utils;

import com.adrabazha.air.attack.alarm.system.telegram.callback.BaseCallbackQueryHandler;
import com.adrabazha.air.attack.alarm.system.telegram.callback.CallbackQueryHandler;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CallbackDataHelper {

    // TODO: 3/18/2022 Replace with reflection
    private static String CALLBACK_HANDLERS_MODULE_NAME = "com.adrabazha.air.attack.alarm.system.telegram.callback";

    private CallbackDataHelper() {

    }

    public static List<String> parseData(String payloadData) {
        return List.of(removeQueryHandler(payloadData).split("::"));
    }

    @SneakyThrows
    public static Class<?> getQueryHandler(String payload) {
        String className = payload.substring(0, payload.indexOf("("));
        return Class.forName(String.format("%s.%s", CALLBACK_HANDLERS_MODULE_NAME, className));
    }

    private static String removeQueryHandler(String payloadData) {
        return payloadData.substring(payloadData.indexOf("(") + 1, payloadData.indexOf(")"));
    }

    public static PayloadBuilder payloadBuilder() {
        return new PayloadBuilder();
    }

    public static class PayloadBuilder {

        private PayloadBuilder() {}

        private String callbackQueryHandler;
        private List<String> dataUnits = new ArrayList<>();

        public PayloadBuilder simpleDataUnit(Object dataUnit) {
            dataUnits.add(dataUnit.toString());
            return this;
        }

        public PayloadBuilder setCallbackQueryHandler(Class<? extends CallbackQueryHandler> handler) {
            callbackQueryHandler = handler.getSimpleName();
            return this;
        }

        public String build() {
            String dataUnits = String.join("::", this.dataUnits);
            return String.format("%s(%s)", callbackQueryHandler, dataUnits);
        }
    }
}
