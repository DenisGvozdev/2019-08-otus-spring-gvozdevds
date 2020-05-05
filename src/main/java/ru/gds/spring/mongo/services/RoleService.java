package ru.gds.spring.mongo.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.mongo.domain.Role;
import ru.gds.spring.mongo.domain.User;
import ru.gds.spring.mongo.dto.RoleDto;
import ru.gds.spring.mongo.interfaces.RoleRepository;
import ru.gds.spring.mongo.interfaces.UserRepository;
import ru.gds.spring.mongo.params.ParamsRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDto> findAll() {
        try {
            return roleRepository
                    .findAll()
                    .stream()
                    .map(RoleDto::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Roles not found Error: " + e.getMessage());
        }
        return new ArrayList<RoleDto>();
    }

    public RoleDto findByRole(String role) {
        try {
            return RoleDto.toDto(roleRepository.findByRole(role));
        } catch (Exception e) {
            logger.error("Roles not found Error: " + e.getMessage());
        }
        return null;
    }

    public List<RoleDto> findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            List<Role> userRoles = user.getRoles();

            List<String> selectedRoles = new ArrayList<>();
            userRoles.forEach((role) -> selectedRoles.add(role.getRole()));

            List<Role> allRoles = roleRepository.findAll();
            return roleRepository
                    .findAll()
                    .stream()
                    .map(role -> RoleDto.toDtoWithSelect(role, selectedRoles))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Roles not found Error: " + e.getMessage());
        }
        return null;
    }

    public RoleDto save(ParamsRole params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getRole()))
                throw new Exception("Role is empty");

            Role roleOld = roleRepository.findByRole(params.getRole());

            if (roleOld == null) {
                return RoleDto.toDto(roleRepository.save(new Role(params.getRole(), params.getDescription())));

            } else {
                roleOld.setRole(params.getRole());
                roleOld.setDescription(params.getDescription());
                return RoleDto.toDto(roleRepository.save(roleOld));
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new RoleDto();
    }

    public String deleteByRole(String role) {
        try {
            if (StringUtils.isEmpty(role))
                return "role is empty";

            roleRepository.deleteByRole(role);
            return "Роль успешно удалена";

        } catch (Exception e) {
            return "Ошибка удаления роли: " + e.getMessage();
        }
    }
}
