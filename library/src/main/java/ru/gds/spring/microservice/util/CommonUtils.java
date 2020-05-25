package ru.gds.spring.microservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.gds.spring.microservice.dto.RoleDto;
import ru.gds.spring.microservice.dto.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommonUtils {

    public List<String> convertStringToListString(String ids) {
        List<String> idList = new ArrayList<>();
        String[] authorIdArray = ids.split(",");
        if (authorIdArray.length > 0) {
            for (String authorId : authorIdArray) {
                idList.add(authorId.trim());
            }
        }
        return idList;
    }

    public UserDto getCurrentUser() {
        UserDto userDto = new UserDto();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null
                || ((authentication.getPrincipal() instanceof String)
                && "anonymousUser".equalsIgnoreCase((String) authentication.getPrincipal()))) {
            userDto.setUsername("Вход");
            return userDto;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userDto.setUsername((userDetails != null) ? userDetails.getUsername() : "Вход");
        Collection<SimpleGrantedAuthority> roles = (userDetails != null) ? (Collection) userDetails.getAuthorities() : null;
        if (roles == null)
            return userDto;

        for (SimpleGrantedAuthority role : roles)
            userDto.getRoles().add(new RoleDto(role.getAuthority()));

        return userDto;
    }
}
