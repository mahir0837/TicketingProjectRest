package com.sarac.service;

import com.sarac.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO dto);
    void delete(String userName);

}
