package com.example.joyh.arduinoAssistant.data.impl;

import com.example.joyh.arduinoAssistant.domain.repository.MessageRepository;

/**
 * Created by joyn on 2018/8/2 0002.
 */

public class MessageRepositoryImpl implements MessageRepository {

    @Override
    public String getArduinoDeviceWebsite() {
        String URL="https://www.arduino.cc/en/Main/Products";
        return URL;
    }

    @Override
    public String getWelcomeMessage() {
        String msg =null; // let's be friendly

        // let's simulate some network/database lag
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return msg;

    }
}
