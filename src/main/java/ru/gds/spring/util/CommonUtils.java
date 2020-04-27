package ru.gds.spring.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CommonUtils {

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
