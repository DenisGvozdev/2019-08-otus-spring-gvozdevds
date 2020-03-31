package ru.gds.spring.util;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
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

    public static Long stringToLong(String val) {
        return (StringUtils.isEmpty(val) || "null".equals(val) || "undefined".equals(val))
                ? null : Long.valueOf(val);
    }

    public static String bytesToString(byte[] bytes) {
        String encodedImage = null;
        try {
            String base64SignatureImage = Base64.getEncoder().encodeToString(bytes);
            encodedImage = URLEncoder.encode(base64SignatureImage, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "data:image/jpg;base64," + encodedImage;
    }
}
