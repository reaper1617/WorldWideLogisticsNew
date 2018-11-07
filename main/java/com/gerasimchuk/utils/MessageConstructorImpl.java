package com.gerasimchuk.utils;

import com.gerasimchuk.enums.UpdateMessageType;
import org.springframework.stereotype.Component;


/**
 * The type Message constructor.
 */
@Component
public class MessageConstructorImpl implements MessageConstructor {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MessageConstructorImpl.class);
    @Override
    public String createMessage(UpdateMessageType updateMessageType, JSONconvertable targetObject) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createMessage");
        return updateMessageType + " " + targetObject.convertToJSONString();
    }

}
