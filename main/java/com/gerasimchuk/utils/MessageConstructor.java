package com.gerasimchuk.utils;

import com.gerasimchuk.enums.UpdateMessageType;

/**
 * The interface Message constructor.
 */
public interface MessageConstructor {

    /**
     * Create message string.
     *
     * @param updateMessageType the update message type
     * @param targetObject      the target object
     * @return the string
     */
    String createMessage(UpdateMessageType updateMessageType, JSONconvertable targetObject);
}
