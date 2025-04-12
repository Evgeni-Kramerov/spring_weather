package org.ek.weather.utils.password;

import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PassHasher {

    public void hashPassword(RegistrationRequestDTO registrationRequestDTO) {
        registrationRequestDTO.setPassword(
                BCrypt.hashpw(registrationRequestDTO.getPassword(), BCrypt.gensalt())
        );
    }

    public boolean verifyPassword(AuthenticationRequestDTO authenticationRequestDTO, User hashedUser) {
        return BCrypt.checkpw(authenticationRequestDTO.getPassword(), hashedUser.getPassword());
    }

}