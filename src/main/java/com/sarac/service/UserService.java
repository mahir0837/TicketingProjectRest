package com.sarac.service;


import com.sarac.dto.UserDTO;
import com.sarac.exception.TicketingProjectException;

import java.util.List;

public interface UserService  {

    UserDTO findByUserName(String username);

    List<UserDTO> listAllUsers();
    void save(UserDTO user);
//    void deleteByUserName(String username);
    UserDTO update(UserDTO user);
    void delete(String username) throws TicketingProjectException;
    List<UserDTO> listAllByRole(String role);

}
