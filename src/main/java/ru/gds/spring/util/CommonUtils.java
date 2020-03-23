package ru.gds.spring.util;

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
}
