package org.ek.weather.utils.mapper;

import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User registrationRequestDTOToUser(RegistrationRequestDTO registrationRequestDTO);

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User authentificationRequestDTOToUser(AuthenticationRequestDTO authenticationRequestDTO);

}
