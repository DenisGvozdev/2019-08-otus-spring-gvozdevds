package ru.gds.spring.util;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {

    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    public static List<Long> convertStringToArrayList(String ids) {
        List<Long> idList = new ArrayList<>();
        try {
            String[] authorIdArray = ids.split(",");
            if (authorIdArray.length > 0) {
                for (String authorId : authorIdArray) {
                    idList.add(Long.valueOf(authorId));
                }
            }
        } catch (Exception e) {
            logger.error("CommonUtils convertStringToArrayList error: " + Arrays.asList(e.getStackTrace()));
        }
        return idList;
    }

    public static List<String> convertStringToListString(String ids) {
        List<String> idList = new ArrayList<>();
        try {
            String[] authorIdArray = ids.split(",");
            if (authorIdArray.length > 0) {
                for (String authorId : authorIdArray) {
                    idList.add(authorId.trim());
                }
            }
        } catch (Exception e) {
            logger.error("CommonUtils convertStringToListString error: " + Arrays.asList(e.getStackTrace()));
        }
        return idList;
    }
}
