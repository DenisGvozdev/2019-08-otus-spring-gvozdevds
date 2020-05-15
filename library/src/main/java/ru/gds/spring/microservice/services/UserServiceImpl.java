package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.microservice.interfaces.UserService;
import ru.gds.spring.microservice.util.CommonUtils;
import ru.gds.spring.microservice.domain.Role;
import ru.gds.spring.microservice.domain.User;
import ru.gds.spring.microservice.dto.UserDto;
import ru.gds.spring.microservice.interfaces.RoleRepository;
import ru.gds.spring.microservice.interfaces.UserRepository;
import ru.gds.spring.microservice.params.ParamsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> findAll() {
        try {
            return userRepository
                    .findAll()
                    .stream()
                    .map(UserDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Users not found Error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<UserDto> findByParam(ParamsUser params) {
        List<UserDto> result = new ArrayList<>();
        try {
            result.add(
                    UserDto.toDto(userRepository.findByUsername(params.getUsername()))
            );
        } catch (Exception e) {
            logger.error("User not found Error: " + e.getMessage());
        }
        return result;
    }

    public UserDto save(ParamsUser params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getUsername()))
                throw new Exception("Username is empty");

            List<Role> roles = roleRepository.findAllByRole(
                    CommonUtils.convertStringToListString(params.getRoleIds()), null);

            User userOld = userRepository.findByUsername(params.getUsername());

            if (userOld == null) {
                User user = new User(
                        params.getUsername(),
                        params.getPassword(),
                        params.getEmail(),
                        params.getPhone(),
                        params.getFirstName(),
                        params.getSecondName(),
                        params.getThirdName(),
                        roles);
                return UserDto.toDto(userRepository.save(user));

            } else {
                userOld.setUsername(params.getUsername());
                userOld.setPassword(params.getPassword());
                userOld.setEmail(params.getEmail());
                userOld.setPhone(params.getPhone());
                userOld.setFirstName(params.getFirstName());
                userOld.setSecondName(params.getSecondName());
                userOld.setThirdName(params.getThirdName());
                userOld.setRoles(roles);
                return UserDto.toDto(userRepository.save(userOld));
            }


        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new UserDto();
    }

    public String deleteByUsername(String username) {
        try {
            if (StringUtils.isEmpty(username))
                return "username is empty";

            userRepository.deleteByUsername(username);
            return "Пользователь успешно удален";

        } catch (Exception e) {
            return "Ошибка удаления пользователя: " + e.getMessage();
        }
    }
}
