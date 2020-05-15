package ru.gds.spring.microservice.util;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gds.spring.microservice.dto.RoleDto;
import ru.gds.spring.microservice.dto.UserDto;
import ru.gds.spring.microservice.params.ParamsBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    public static List<Long> convertStringToArrayList(String ids) {
        List<Long> idList = new ArrayList<>();
        String[] authorIdArray = ids.split(",");
        if (authorIdArray.length > 0) {
            for (String authorId : authorIdArray) {
                idList.add(Long.valueOf(authorId));
            }
        }
        return idList;
    }

    public static List<String> convertStringToListString(String ids) {
        List<String> idList = new ArrayList<>();
        String[] authorIdArray = ids.split(",");
        if (authorIdArray.length > 0) {
            for (String authorId : authorIdArray) {
                idList.add(authorId.trim());
            }
        }
        return idList;
    }

    public static UserDto getCurrentUser() {
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
        Collection<SimpleGrantedAuthority> roles = (Collection) userDetails.getAuthorities();
        if (roles == null)
            return userDto;

        for (SimpleGrantedAuthority role : roles)
            userDto.getRoles().add(new RoleDto(role.getAuthority()));

        return userDto;
    }

    public static UserDetails getCurrentUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public static JSONObject mapToJSON(Map<String, Object> map){
        return new JSONObject(map);
    }
}
