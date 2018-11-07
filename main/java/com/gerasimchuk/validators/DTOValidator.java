package com.gerasimchuk.validators;

/**
 * The interface Dto validator.
 */
public interface DTOValidator {
    /**
     * Validate boolean.
     *
     * @param dto the dto
     * @return the boolean
     */
    boolean validate(Object dto);
}
