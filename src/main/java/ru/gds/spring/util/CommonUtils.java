package ru.gds.spring.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

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

    public static String getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (authentication!=null) ? (UserDetails) authentication.getPrincipal() : null;
        return (userDetails!=null) ? userDetails.getUsername() : "Авторизация";
    }
}
