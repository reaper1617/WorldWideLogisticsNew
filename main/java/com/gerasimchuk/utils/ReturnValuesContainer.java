package com.gerasimchuk.utils;

import com.gerasimchuk.enums.UpdateMessageType;

/**
 * The type Return values container.
 *
 * @param <T> the type parameter
 */
public class ReturnValuesContainer<T> {
    private UpdateMessageType updateMessageType;
    private T returnedValue;

    /**
     * Instantiates a new Return values container.
     */
    public ReturnValuesContainer() {
    }

    /**
     * Instantiates a new Return values container.
     *
     * @param updateMessageType the update message type
     * @param returnedValue     the returned value
     */
    public ReturnValuesContainer(UpdateMessageType updateMessageType, T returnedValue) {
        this.updateMessageType = updateMessageType;
        this.returnedValue = returnedValue;
    }

    /**
     * Gets update message type.
     *
     * @return the update message type
     */
    public UpdateMessageType getUpdateMessageType() {
        return updateMessageType;
    }

    /**
     * Sets update message type.
     *
     * @param updateMessageType the update message type
     */
    public void setUpdateMessageType(UpdateMessageType updateMessageType) {
        this.updateMessageType = updateMessageType;
    }

    /**
     * Gets returned value.
     *
     * @return the returned value
     */
    public T getReturnedValue() {
        return returnedValue;
    }

    /**
     * Sets returned value.
     *
     * @param returnedValue the returned value
     */
    public void setReturnedValue(T returnedValue) {
        this.returnedValue = returnedValue;
    }
}
