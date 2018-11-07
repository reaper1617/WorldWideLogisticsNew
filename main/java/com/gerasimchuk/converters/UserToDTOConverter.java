package com.gerasimchuk.converters;

import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.entities.User;

import java.util.List;

/**
 * The interface User to dto converter.
 */
public interface UserToDTOConverter {
    /**
     * Convert user dto.
     *
     * @param u the u
     * @return the user dto
     */
    UserDTO convert(User u);

    /**
     * Convert list.
     *
     * @param users the users
     * @return the list
     */
    List<UserDTO> convert(List<User> users);
}
