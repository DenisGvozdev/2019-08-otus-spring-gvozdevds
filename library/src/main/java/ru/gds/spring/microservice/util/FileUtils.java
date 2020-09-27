package ru.gds.spring.microservice.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    private static final int COUNT_WORDS_ON_PAGE = 170;

    public static byte[] getFileBytes(String filePath) {
        logger.debug("start getFileBytes: " + filePath);

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new Exception("file not found: " + filePath);
            }
            if (!file.isFile()) {
                throw new Exception("this is directory: " + filePath);
            }
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

            return bytesArray;

        } catch (Exception e) {
            logger.error("file not found: " + filePath);
            return "".getBytes();
        }
    }

    public static MultipartFile byteArrayToMultipartFile(byte[] bytes, String fileName) {
        try {
            if (bytes == null)
                throw new Exception("byteArrayToMultipartFile bytes is null");

            if (StringUtils.isEmpty(fileName))
                throw new Exception("byteArrayToMultipartFile fileName is empty");

            FileItem fileItem = new DiskFileItem(
                    "fileData",
                    "application/text",
                    true,
                    fileName,
                    100000000,
                    new File(System.getProperty("java.io.tmpdir")));

            return new CommonsMultipartFile(fileItem);

        } catch (Exception e) {
            logger.error("byteArrayToMultipartFile error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    private File getFileByFilePath(String filePath) {
        logger.debug("start getFile: " + filePath);
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new Exception("file not found: " + filePath);
            }
            if (!file.isFile()) {
                throw new Exception("this is directory: " + filePath);
            }
            return file;

        } catch (Exception e) {
            logger.error("file not found: " + filePath);
            return null;
        }
    }

    public File getFile(String filePath) {
        try {
            File file = ResourceUtils.getFile(filePath);
            if (!file.exists()) {
                throw new Exception("file not found: " + filePath);
            }
            if (!file.isFile()) {
                throw new Exception("this is directory: " + filePath);
            }
            return file;

        } catch (Exception e) {
            logger.error("file not found: " + filePath);
            return null;
        }
    }

    public String getTextBookFromFilePath(String filePath) {
        List<String> bookPageList = new ArrayList<>();
        try {

            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                //if (!org.springframework.util.StringUtils.isEmpty(line)) {
                    text.append(line + "\\n");
                //}
            }

            reader.close();
            return text.toString();

        } catch (Exception e) {
            logger.error("file not found: " + Arrays.asList(e.getStackTrace()));
        }
        return null;
    }

    public List<String> getBookPages(String filePath) {
        List<String> bookPageList = new ArrayList<>();
        try {

            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (!org.springframework.util.StringUtils.isEmpty(line)) {
                    text.append(line);
                }
            }

            int countWordsOnPage = COUNT_WORDS_ON_PAGE;
            StringBuilder page = new StringBuilder();

            String[] words = text.toString().split("\\s+");

            for (int indx = 0; indx < words.length; indx++) {

                if (countWordsOnPage == 0 || indx == words.length - 1) {
                    page.append(words[indx]).append(" ");
                    bookPageList.add(page.toString());
                    countWordsOnPage = COUNT_WORDS_ON_PAGE;
                    page = new StringBuilder();

                } else {
                    countWordsOnPage -= 1;
                    page.append(words[indx]).append(" ");
                }
            }
            reader.close();

        } catch (Exception e) {
            logger.error("file not found: " + Arrays.asList(e.getStackTrace()));
        }
        return bookPageList;
    }

    public File saveFile(String name, String directory, byte[] bytes) {
        try {
            Path path = Paths.get(directory + name);
            Files.write(path, bytes);
            logger.debug("File " + directory + name + " success saved");
            return getFileByFilePath(directory + name);

        } catch (Exception e) {
            logger.error("saveFile error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    public MultipartFile getMultipartFile(String filePath) {
        try {
            File file = getFile(filePath);
            if (file != null)
                return new MockMultipartFile(file.getName(), new FileInputStream(file));

        } catch (Exception e) {
            logger.error("getMultipartFile error: " + Arrays.asList(e.getStackTrace()));
        }
        return null;
    }

    public static String getDefaultBookTitleBase64(){
        return "/9j/4AAQSkZJRgABAQEBLAEsAAD/4Q0JRXhpZgAASUkqAAgAAAACADIBAgAUAAAAJgAAAGmHBAABAAAAOgAAAEAAAAAyMDIwOjA5OjIwIDE0OjAzOjI5AAAAAAAAAAMAAwEEAAEAAAAGAAAAAQIEAAEAAABqAAAAAgIEAAEAAACXDAAAAAAAAP/Y/+AAEEpGSUYAAQEAAAEAAQAA/9sAQwAGBAUGBQQGBgUGBwcGCAoQCgoJCQoUDg8MEBcUGBgXFBYWGh0lHxobIxwWFiAsICMmJykqKRkfLTAtKDAlKCko/9sAQwEHBwcKCAoTCgoTKBoWGigoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgo/8AAEQgAoABqAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A9c8Y+JLrR7yK2slgknliDRxOjM0jFsbRhh/9brWU/inxEI7RlsISZBmb/RZP3fJ/2+ewx759qk8bPEPFulLJbGR2RNsvaP8Aedz2z+vSueklj0mDVIE1wEvKA8w+Z7Nif9Xzn5csMemT+HmNu56KSsdEPE+vG7u0NlEIYwfKf7M53/hv7fr7VB/wlXiT7DFJ/Z0IuGch4vs7nYvHOd/OMk++McV2djpkVtvZ3Mzser8gDsAOnTHNW/s8P/PKP/vkVVmTzLscQfE2vf2gIhZRfZvL3GX7M/3sfd+/+v4e9QDxV4k+xTyf2bCbhXCxxfZ3G9eec7+M4H0z3rvvs8P/ADxj/wC+RR9nh/55R/8AfIos+4cy7HDnxPrwuLRBZRGKQfvX+zONnPpv7/p1pieKPEJ+2brCJfLGYf8ARZP3nI/2/qPwz7V3f2eH/nlH/wB8ij7PD/zyj/75FFn3DmXY4N/FPiNba2cafD5ztiVPsznyx6/f5x+vtUv/AAk2vfb5ojZRC3VWKSfZnO44PH3/AKfXOOK7f7PD/wA8o/8AvkUfZ4f+eUf/AHyKLPuHMuxwP/CVeJP7P806bF9p348n7O/T1zv9Of0qx/wk2vfb4IxZRG2dVLy/ZnG04HH3/r9Md812N1YQXEDRsuzP8UZ2kfiK888QP9luNYsZ9XCR7Yi8zqALT5QSy+pJBIGTg4/FO6Gmn0NDSPF+py6tBZ6lDbW5mmVI1MLI0iE43Llj6HjnGOetd5XltvFDZa/4ftihvGHlBLvJPG9sMSSTkj869SoiTPyOH8Z/a/8AhJ9N8gp9n2J5mev+sPT+vt0ro9L0yNbScXUccouHZ2RlBXByOnuOtcv43SJvFulM9y0cgRNsQPEn7zuO+P0613Vv/qI/90fyprcHsh4AA4AAHSlpQKUCrsRewmKMU8CjFOwrgQMkYFMxUh/HFGKbQiPFJUmKQilYq4yql1YQTwToI0Rphh3VRk9Ovr0H5VcxSVLQ0ed3Ed9D4p0iJfLEKGJZh3yGI+X+ufwr0OvP9bSJvHmms9yY5BLHtiHST526jvj9K9AqEVLocH42kiXxZpSvbNJIUTbKOkf7w9T2z+vSu6th+4j/AN0fyrivGX2v/hKNN8gIbfYnmkjn/WHp/X2rt7YfuIv90fyqorVik9ESAUuKUUoFapGYmKXFKBTsCqsK4zFGKfRgUWFcjxRinkelNIpNDuMIpCKfTTUtDPO9beJfHmnK9s0khlj2ygcR/O3U9s/rXoGK4XWPtf8Awm9h5AQ2/mp5hI5++3T+v6V3VZI1lsjhPG0cTeLdKZ7kxuETbFniT953HfH6da722/1Ef+6P5Vwfjd4V8XaSsls0kpRNkoHEf7w9T2z+vSu9tv8AUR/7oq4bsmeyJMU4Cgc+9LWxlcKKKDTEITilGe9AHeloEJQRmiikMaRTTUh6ehph60mikzznXI4m8e6cz3JjkWWPbF2k+duo74/SvQMVwOuNCPH2miS2aSUyx7JQOI/nbOT2z+vTtXf4rFLc1k9EcT4zF3/wlOm+QqG32J5pI5H7w9P6+1dvb/6iP/dH8q4PxvHG3i3Sme68t1RMRZOJP3h6+uP0rvLb/UR/7o/lVx3ZMtkTjpRRRWpkLRigUtABRRRQAhpKU0lABTDT6a1JjR5/rH2v/hOLDyVU23mx+YSOR856f1/Su8rz7W4o28e6c7XXlusseIs8SfO3X1x29K9BrKPU0nsjgfG7wDxdpIkt3klKJslHSP8AeHOT2z+vSu9tv9RH/uj+VcR4z+1/8JRpvkRq1vsTzWK5I/eHpx+ddvb/AOoj/wB0fypx3YS2RPRTRz9KdWpkKKWkooAWijFFMQhpKU0lIYU1utOPSo8UmNHnmuPAPH2mrJbu8plTZKOkfztnJ7Z/Xp2r0GuD1j7X/wAJxYeRGrW3mx+YxXJHznp/X9K7ysY7s0lsjgfG0cb+LdKdrry3VI8Rc/vP3h68c4rvLf8A1Ef+6P5VwXjZ7ceLdJEsDvNsTy5B0j/eHOTjjP69K722/wCPeL/dH8qIvVjlsiZadTBTga2RkxaKKKYhc0UlFAgoooJxSGNPvSGgmkpNlJHnmuRxt49052ufLdZY8Rc/vPnbrx2r0DNee648A8faYJIHecyp5cg6J87ZyccZ/Xp2r0GsUzSWyOG8Zfa/+En03yIla32J5rlMlf3h6HH5+nWu3tj/AKPF/uj+VcH42iR/FmlyNciMokf7rn95+8Pt2/8A18V3Vsf3Ef8Auj+VKL1Y5LRFilBpgOadmtUzMeDS5qPNLmquKw+jNMJx1oBzTuKw4n0ppNGaSk2OwUhNBNNJqGxnB6x9q/4TaxMMStb+bH5jFMlfnPQ4/P0rua8/1qJH8eafI1yI2WWP91z+8+duvHbt+tegVkjWWyOC8cNbjxdpIlgd5tieXIOifvDnJxxn8M9K7u1/1MfrtFcV4yF3/wAJPpvkQq9vsTzXKZK/vDjBx/hjrXUaTeedayecuzyWKFiMKQCcHP060ReoS2RpsfXrQD60wdOKKu5FiQGjNMzQTVXFYdnNLTc0ZouFh2aTNNzSUrhYkBOOM59qY3JpKhnuEihlkAMnl/eVBk/TFJsaRwettbjx7pglhd5/NTy5B0T52zzjjP156dq9Brz26lvZvFWkyLAjQyGJpnCZ2ksTwef58V6FUIuXQ4PxtEreK9Lka5WPYkeYjn95+84zx2/DnrxXNSJZ6xb6vNBo0hVJQbiFOXunHBdR/dGPofm46g9l408O3mrX8F3YLELiCLbDI8m0xvuzkZRuPX1HHvWa/h/xN5VoqTwgqMT/AL5Pn5PT9z9Ow44x/FUtalp6I7Wxv47reux4XQ4KOpXjsefYjire5f7wrgl8P+Iftd2zSQm3YHyVEqAqf+/P+OP9rrUP/CO+KPsESefb/aw5Lv5iAMvHA/c8d8enXLdBV32J5V3PQ9y/3h+dG5f7wrgv+Ef8Q/2gH8yH7L5eCnmpnfjr/qen4+/+zUK+HfFH2KdTPb/aS4MT+ahCrzwf3PPbPr2K9CX8g5fM9E3L/eH50m5f7wrgT4f8RfabRlkhEKj9+plQlue37n/DPTjrTU8PeJB9r3ywkOP3GJY8ocjr+5+vPPHGP4qOZ9g5fM9B3L/eH50b19R+deeSeHvE5trZVntxMrfvm81AHHt+5OP6e/Wpf+Ef8Q/b5nMkP2UqwjQSoCGwcH/U/TucYyd3Sjm8g5fM7i6uoraFpZCdq/3QST+ArzTxHDBPd63f3GjzeUyRCeKQbTdfKo2p/tDpnuQOemLh8O+KP7P2C4t/te/PmeYmNvpjyfTj68+1WRoPiIX0DrLCLUKvmJ5qEseOf9T9e4z229KTuxpW6mTayQX+u6BdwSLaxHySltgjK722gjHBA7djnOa9SxXn+k+FdXGs217qnkytBMrRuJgTGgOSABEvJzye/Hpz6DTiu4pn/9n/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCAEsAMgDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3/Ur620zT7i+vpRDa26GSWQgnao6nA5rn7jx/4atrhLe41LypnQSKjwSKSpBYHlehAJHqKteP3mi8Fa1JahTcJbFo92MbgQRnPHX1ry/zpfHBu7Gx1FLbQoYLe4mM1mrSSXBA3AEDIUMVPTbjIXA4HmylbY9CMU1dnoq/EDww2nNqC6kTYrIIjP8AZ5dgcjcFzt645+lPsfHnhu/vFtLLUfPuWTzBHHBIxK7d2fu/3ea56O11v7KhPiDTzqDOFjuTpZC7Nu3Z5WcEk8569hxxVTQtAvdDVrNtatZbHe0slu9gUcysOTvXBA3c7fwORkFczHyxt/X+R0n/AAsrwj5Pm/2zH5e7Zu8mTrjOPu+nNWLvx54bs7pba71HyLhkEojkgkUlSu4H7voM1xg0TWP7XTVW8T2J1BYfswf+yRs8rHTb/ezznPtjFS6/oV5rFt9ifXbSHTmKO0K2BZjIo+9vPIG7B24wBwMDgHMw5Y33/r7jqx8QPDJ046gNSJsRJ5Pn/Z5dm/G7bnb1xz9KfZePPDd9eC0stR8+5KeYI44JGO3buz93+7zXOrBrX2X/AJGDTjqO8Yuf7MO0R7cbfLztznnd17dOKp6Bod3o0P2RNctJtOLNK0LWBRjKw5beuCBu52/gcjIJzMXLG39f5HS/8LL8IeV5n9sx7N23PkydcZ/u+nNWbzx54bsrsWt5qJguCgkEckEinaV3A/d/u81xZ0PWDq41b/hJ7D+0hD9mEn9kjZ5WOmz1zznPtjFS6/od5rFv9jbXLOHTiUkMK2BZjIo+9vPON2Dtx04GBwDmY+WP9f8ADHWf8LA8M/2d/aH9pH7D5nk+f9nl2b8btudvXHP0p9l488N3159ls9R8+52eZ5ccEjHbt3Z+7/d5+lc6LfWvsv8AyMGnf2hv/wCPn+yzt8vbt2eXnGc87uvbpxVLQNCu9Gg+xrrlnPp+55TC1gUbzGHLb15xuycfgcjgnMw5Y2/r/I6f/hZXhHyTKNZj8sNsJ8mTrjP9305zVi88eeG7K7+y3mo+RcbPM8uSCRTt27s/d/u81xY0TWBq/wDa3/CT2H9peT9m8z+yRs8rH3dnrnnOfbGKm8QaFeaxB9lOuWcGm7ll8hbAu3mKOG3tzjdg49OBgcA5mHLG+/8AX3HV/wDCwPDP9m/2h/aR+w+Z5Pn/AGeXZvxu2529cc/Sn2fjzw3e3ZtbPUTPcBDIY44JGO0LuJ+7/d5rnfs+tfZcjxDp39obv+Pn+zDt8vbt2eXnGc87uvbpxVPQNDu9Gtvsaa5aTWG55BC1gVYSMOW3rzjdk7cdPlORnJzMOWNv6/yOlPxK8IiPzP7Zj2btmfKk64zj7vpzVm88eeG7G8Npeaj5FyE8wxyQSKdu3dn7v93muLGh6wNXOrf8JPYf2k0P2Yyf2SNnlY6bPXPOc+2MVL4g0K71iL7Kdcs4NODLKIFsC7CRRw3mNyRu52/hwMAHNIOWN/6/yOsPxA8MjTRqB1IixMnkif7PLs343bc7euOfpT7Tx54bvLpra01Ez3CoZDHHBIxChdxP3egHNc6YNaNrkeIdOGoFjm5/sw7fL242eXnGc87uvbpxVPQNCu9GtvsS65aTaeC8iQmwKsJGH3i45I3ZO3GMcHI4Jdi5Y2/r/I6b/hZPhIR+Z/bEe3O3PkydcZ6bfQ5qxe+PPDdjeNaXuo+RcqnmNHJBIrBdu7P3f7vNcWND1kau+rDxPY/2i8P2dpP7JGwxY6bfXOTnPtjFT674fvdcC2aa3axWIdZkt0sC7iVRw29skjdzt/DgYAOaQ+WN9/6+46pviB4ZXTRqDakRYmQwic28u0uBuK529cc/Slt/H3hq4uXt7fUvNnRDIyJBIxCgBieF6AEE+grAe21s2bka/p41BXKyXI0sldm3bs8rOAQec9ex44rmfOk8DfZLK/1FbnQ5oLieAw2arJHOAduSRlgWDHpjHDZHBOZoFFPb+vwPZtNvrfUrC3vbGUS2twgkicAjcp6HB5orI8APNL4K0WS6Ci4e2DSBcY3EknGOOuelFWZSVnYT4hJbyeB9cS9d47VrVhM6LuZUyNxA7nGa5SwjtY9f1OT979p/su08yLyQgESt+6YY6sRkkfwkYGcZPV/EGOSbwRrkcEAuJntWVISu4SMSMLjvnpXD+ENLuNE1jU/3tzcqbG0KXlwp371IDRA+kZGMdQSQeRUS3NI/CzC0Cx1DQvEtvLqkUy+HZJG8lp5lka1klKgNJj7nG4DP3N3Y5rrZbjxBd+O7O106cWmieXckLHEFkTYIwyEMu0ncV2vyBvYckUlxqut3nie/0O10W/fR7ZVQMgjRVYgZWRixVkdcn+8A2cHIqroUur6N47sLCewu49DmLW8FytvhndYwNsrHJ8oNuKdOx5xS2Kd3q+x2ei6deTML7VJ7uOdnV/s/m4C7RgBtuAwPORgA9cV0AA9KTPNLWiVjG4YxRgelFFMAwPSjA9KKXp9aBiYA7CjA9KKKLAGB6UYHpRRRYQYAowPSiigYYHpRgelFFABj2FJgUtFAjn9Z068hZr7S5ruWdXZ/s5lyG3DBC5yFA4wMEDriuXjuvEFn47vLXULgXWieXbEpJEGkfeJAqAKNoO4NufgHYo4Jr0cnArzfWG1LVPGd5bx2lzLpEZEEsjW25kYxkYjYEHyywBbr0J4yKlouL3RyniDT9Q1rxJc3GkxTP4ejdfO8iZY2u5IiwLx5+/xgHGN+zucGupvo7WXX9MkzL9o/sy78uMwhwYmb96xz0YDBA/iJwcZyHW+q63ZeKLHQ7nRb9NHuVdCziN1LAHCxsGCqiLg/3iFzgYNZ/i/S7jW9Y0sia5t0Fhdl7u3Ul97EhYsjs5OMdSQAOTUF3u7M7j4epbx+B9DSyd5LVbVRC7rtZkydpI7HGKKPh7HJF4H0OOeAW8yWqq8IXaI2BOVx2x0orRbGM92J8RWZPAmvMk32dltGIm5/dnI+bjnjrxzXCeE9QudR1jVnu7WaxK6bZAWkrsxGSCZeePn/AD+Xnmu8+IUkMPgfXJbuHz7dLVmli3bd6gjK57ZHFcnpl1az69qaQKrzrplo7zrOZAY2bMcfPQoMjP8AFnceSRUS3NY/C/67HUaFayXmpXGpSSqix3EyLFA5KM3CMW55PyDt1BxxXSg4opKtIybCloopgkFFLilphcTFFLilxTsK42pFGApGNzdCewpMUoxgDIBHTPQ00iWw3ZBy28Drxg/UUwjBI9KdgAEcZIxwc4oPJJxTaBMZRT8UmKmw7jcUlOIpKVh3EopcUhosO4Udu9FFIDm9ctZLPU7fUo5UZJLmFWjnchFblFK88H5z26kZ4rhvFmoXOnaxpD2ltNfFtNvAbSJ2UnGSJeOPk/MbuOa9drzPUrm1g13TFnRUmbTLtknacxgIrZkj46lxgZ/hxuHIAqJIuB1Xw6Zn8CaCzzfaGa0Umbn94cn5ueeevPNFL8PZIZvA+hy2kPkW72qtFFu3bFJOFz3wOM0VS2Jnux/jyOabwbrEVrKsNw9uUjkZtoRiQASe2D3riPDWiwaPqOprYCOKylsrbEKzbyJVYCR8dg3y4PRgMjgiuw+JXl/8K/8AEPn7/K+xvv2Y3Y4zjPGfrXDeFP7QGu6uda8j7f8A2XY4FvjyxF/B77vvZ7dMVEtzSN+Vnr1FApRWhlsGKWgClAqkhNiAetOooxTsSFFLS0wExRilpKADFGKKWgBCKSnUUANoNLSYoAaR6UmKfSEZpWGNxSU48UmM1JSYmK8q8S6NBrGoaWt+I5bGOxuP3LTbCZWYiN8dwvzZPRQcngGvVSePavIfFf8AaP8Ab2jf2L5Bvv7Kvs/aMeX5X8fvu+7jt1zWcy4XvoegeA45ofBujxXUqzXCW4WSRW3B2BIJB75PeioPhr5f/Cv/AA95G/yvsabN+N2OcZxxn6UVS2Jnuyx47kmh8G6xLbRLNcJbl44mXcHYEEAjuCe1cP4c1qDWNS1NrAxy2UdjbHz1h2EyswMiZ7hflAHRQcDgCu0+IUcM3gfXIrqbyLd7Vlkl27tikjLY74HNcnpdpa2+vam8DIk7aZaI8CQGMBFbEcnPXeMnHVcYPINS1qXG3L/XkenCl60AZp1apGNwooFLVCCiilAosAUUtFVYVxMUtLijbRYVxKKXbRinYBuKMU7FJSsFxKKWkxSsO4lJS0UhiU0040UrAMIrynxHrMGj6hpZ1AxxWMljc5maHeRKrExpnsG+YEdGAweCa9XIxXmOp2lrca9pbTsjzrpl2iQPAZAUZsSScdNgwcfxZwOSKzka02up13gOSafwbo8tzEsM724aSNV2hGJJIA7AHtRTfh7HDD4H0SK0m8+3S1VY5du3zFBIDY7ZHOKKFsTPdjfiKpbwJryrB9oY2jAQ8/vDkfLxzz045rhfCen3Wnaxq6Xl1LfM2m2RW6kRlPUfuuePk/P5uea7z4gvLD4H1ySCcW8yWrMkxbaI2BGGz2x1rhvCerzaxqmrPJHdWsEen2m22uGO8OxDPLg9nJznuQSeaT3NI/Cz1wdKUUClrUwClpKWqSAKWgDNOAxVEiAUtFFABRS0lAgpaSjNAXCg0tJQMQikp1GM0AMopSMUlJoaYlJS0VIxp5FeQ+LLC61DV9Ijs7mWxK6beFruJGYjqPK44w/5nbxzxXr56V5D4r1ibSNU0hkjubqGTT7vda27HeXUlklwOyEZz/CSCOcVnM1pXvodx8OlZPAmgq8H2dhaKDDz+7OT8vPPHTnminfD6SSbwNocs84uJntVZ5g24SMSctnvk80UuhM92L8QWt08D6498jvaLasZlQ4ZkyNwB7HGa5bT5beTX9SRRIbtdLs2dzKHXyi2YlAH8QGdx/iPI4IA6zx5FPceC9Zhs9v2mS3KRbsY3kgDOeOvrxXD+FtBTQ9R1RbNHSwlsrYASSK7ecjBZeRzjIAHbgleDkj3Kjax6wKcASMjp6mkp5xwMZAXIFbJGLYm0gZ4I9QaAM04dc4AOQDjuDSL0qrCuLRRRQAUtJRTEFLSUE0AI2cjBoxmgc0tAtwooooGFLRSUgCm4OcDk06l/gYjrwPwoGNKnttJ9M0ypMDdt2jbnHv9aY3OCepHNJoaYhrzG/lt08QaWrrILttLvGSQShFEQbMqkH+IjG0/wnk8DB9OPSvJfE+grrl/paXaO9hFY3AIjkVG89mKxc9cZJB7cgtwMjKZrTtfU7b4etbv4G0N7JHjtWtVMKO25lQk7QT3OMc0U/wHDPB4L0aK82/ao7cJLtxjeCQcY4xn04ooWxMt2Q/EsJ/wr7xAJmdI/sb7mQZYDjJAyMn8a4Xwk2oPr2sNrNtFaXv9l2AEUIGzyhwjZ/vHkEY4wBXoPj+RofBWtSpbrdOlsWEDKWEpBB2kDqD0xXFeHdTtNS1XU/7OW2lt00+1JuoYyuXZgTET6RjAA6qCAfUzL4kXF+6/67HqtOB4AIzjpzgikpa3RixR2xwBzS0DpS0xCUtFJTEFFFKKAEoAz1pSKWmIKTFLRQMTFFLSGgBKKKKQBSg4Pr2IopKQCHA+6CD7nOKb2p56U2hjQ2vHvFbX6a5o7aRbRXd7/Zd+vkzAbPKPDNn1HAAxzkjjv7Ea8o8Q6pa6dqmlnUVtYrd9PuiLqaMttdWJEQPQCTkEfxAED1GMzanudf8ADUJ/wr7w8IWZ4/sabWcYJHOCRk4P40VP4AkabwVosr262zvbBjAqlRESSdoB6AdMUU4rQifxMT4gxLP4I1uF7gWySWzI07ZxECQCxxzx1/CuO0SwsrLWdTNibaIvp1or20KMuArALLzxiQcjvgDPOcdb8Scf8IB4g3RGZfsb5jUkF+nAI5GenFcH4St7221zWE1O8+33B0uxYXIGBsOMR46ZXrnqdwzUy3NI/Cz2IUo60gpR1rZGDHUtFJTELSUUUwFFLQKKYBRRRSAKKKKACiiigBDRQaSgBaKSloASm0+mHrSGIa8r1uws7zWdLa8a2k2abdqtrMjNuDNhpQB2jHJ74JxzjPqhrx7xZb3tzrejppl59guBpd8xuSCRsGcx46ZbrnqNpxWVQ1p7nffD2JIfA+iRR3C3MaWwVZ1ziUAkBhnnnrRTPhtt/wCEA8P7YmhX7GmI2JJTrwSeTj3opx2Jn8TJPiBLPB4J1qa0lWG5jtmeKRmChGBBBJPAwfWuF8Ka62uanqrRG5SzisbYrDc4DiV2DSvt64YkHPQnJFdz8Q/s/wDwg2ufbvM+yfZW87ysb9mRu2574zXLWH2f/hIdTx5/2z+y7Pfv2bPJ3fusbf4sZ3ds9OMVEtzSNuV/12PTBTl600U5etbIwY6kopaYhKWikpgKKWkFLTAKKKKEAUUUU2AUUUVICGkpTSU2AUtFFIAprdaWkbrSGNNeSeKNcbRNR0p5TctZSWNyWit8FzMjFon24zhSCc9AcE+letnpXmd99n/4SDS8if7Z/Zd5s2bNvk7v3ud38WMbe2evGaymaU7dTq/AE09z4K0We7kWW4ktg8jqwYMxJJII4IJ9KKb8PPs3/CDaH9h8z7J9lXyfM+/sydu7HfGKKcdhT3YvxAinn8E61DaRLNcyWzJFGyhg7EgAEHggn14rhvCmgtoWpaqIhcPaS2NsFluMFzKjBZUz1wpAGOgOcV2vxIKjwDr5eQxKLN8yAElOnOBycda4Pwlc311rusSapZ/YLkaZYqLYcjYMYkz0y3THbaM1ErXNI35WexDmlHWkFLWyMWO70meaTGWBHWnAYpki0lFFMBRS02nUwCiigDjJ4H86QgopSBxwyntnvSUwuFFFFIYhooooAKSiloAQ8UwmnOMimAetIYua8j8U6E2ualpKyi4SzisbkNNb4DiV2KxJuPOGJIx0JwDXrhrx3xZcXttrWjvpln9vuDpd8ptjwNhzmTPTK9MdTuOKxqGtO99D0D4fxT2/gnRYbuNYrmO2CSxqoUIwJBAA4AB9KKj+G2P+EA8P7ZTMv2NMSMCC/J5IPIzRVR2Jn8TH/EGVIPBGtyyW63MaWzM0DZxKAQSpxzz0rjtE1CzvdZ1RbJbeTZp1ozXMLltwZgRESeMRjgd8EZ5znsvH0bTeCdaiS4W1Z7YqJ2YqIiSBuJHQDrmuL8PaXaabqup/2c1rFbyWFqDbQSFtrqwBlI9JOCD/ABAAn0ES3NI25f68j1WgmkNOFaox6Dk6UtNBwafVCEpaKKYhKWkooAWnjhVPoT+FR0oJByDTuJij7rc54/Wg9TSEk9TmjNFwSCikpaQxKKKKACgnFB4FIB3NIBDRQaQ0mNCV5ZrN9Z2etaUt4tvGX067ZbmZyu0KxLRAjjEg4PfAOOcY9TryrxBptpqOp6YuoNay26afdD7NNIV3MzECUDoRHyWPUAkj0OUzWFr6nZ/D6VJvBGiSx262yPbBlgXOIgSSFGeeOlFL4BjaHwTosT3C3TJbBTOrFhKQSNwJ6g9c0U09CZbsg+JJQfD/AMQ+crvH9jfcqNtYjjIBwcH8K4bwiuoJr2sjWbmK6vP7LsCJoSNnlnlVx/eHJJzzkGvQPHcs8Hg3WJrTaLmO3LxbsY3ggjOeOvrxXDeGNeTXdR1R7N3awisrcr5iKjec7BpemDjJBHbk7eOBm/iNY35X/XY9apaatOrZGItKppopapEseaSkBpaYBSiiimIKSlooASiiloAKSiloASiilpAFNJ7UE0lAwpKDRUtjQHp71454rXUH13Rl0e5itL3+y79jLMRs8ocsuMfePBBzxgnmvYTXk/ijX10O/wBLa8d0sJbK4LGNFdvORi0XB5xkEntwA3HByntqa0730Ox+GpQ/D7w95KOkf2NNqu25lHOATgZP4UVN4Clnn8GaNNebftMluHl24xvJJOMcYznpxRTWwp7sZ8Qlt38D64l67x2jWrCZ0XcypkbiB3OM1ythFbp4g1NlaQXZ0uzV4zEEURBv3TZH8RGcj+E8DgAnqviDHLL4H1yO3gFxM9qypCV3CRiQAuO+TxXC+E9Hm0jVNWV5Lm5hk0+023Nyp3l1IV4s+iEYx/CSQec1D3NI/Dv/AFoevDpSimqaWtUYDqBSA0tMBaUGmilqrk2H/SkptKDTAWlpBzS0AFJRS0AJRRSE0AL0pCaTNFABSE0GipbGkH86Q0UlIYHpXmV/FbPr+mM7SG7Gl3aogiDqYi371iT/ABAY2j+I8Hgkj0wmvJPFWjy6rqmkhJLm2ij0+6zc26ksHYlUjz6OTjb/ABEAemc5mlPc7j4erbx+B9DSyd5LVbVRC0i7WZMnaSOxxjiij4fpLF4I0SO4hFvOtqqyQhdojYE5XHbB4opLYU92N+IrMngTXmSb7Oy2jETc/uzkfNxzx145rhPCWoXWoazqz3lrLYsum2YW0kdmI6HzeePn/P5eea734gPFF4I1uS5g+0QJas0kO7b5igjK57ZHGa5LTLu0uNd1NLdUknXTLR3uEnMoKM2Uj56bBkZ6tnJ5JqXuaR+F6f1oen59KUHNMFLWiZlYfSg00GlqiRxopM0UwFzS02lp3FYWikzRTuFhcmimlsEcUZJNFxWHUUlJRcdhc0UlFK4xaTNGaSkAUhNBNNpNjFPTNeQeLNQurDWdIe0tpb5n028DWsbspPU+bxx8n5/NxzXrpNeZand2lvrumJcKiTtpl26TvOYwEVsyR8dd4wM9VxkcgVlM0p6M6v4dMW8CaCzTfaGNopM3P7w5Pzc889eeaKX4eyQzeB9DktYfIt3tVaOLdu8tSThc98DjNFC2JnuyTx5HLL4M1iK1lWG4ktykcrNtCMSACT2APeuH8N6Nb6RqOprp4iispLG2xAk/mESqwEj47BvlIPRgMjgiuw+Jfl/8K+8Q+fv8r7G+/ZjdjjOM8Z+tcL4U/tH+3tY/towG+/suwwbfHl+V/B77vvZ7dMVMtzWHws9fqQfLjBAOMlvSo6fwwHI3YwQeM1qjFjs7hy24HjJGCDTAfWlJx6E5BOPamim2Kw+imilz607gOzRmkooELRSU0n0pgO6mlFIOKKLgLRmkooAM0UUhNIBaUYAZiM46D3plKpGCrHAPf0NFx2Hlmzt3gt/dI4+lRNjII6EZp2cNlip5zwepph7ewpNggryrxJo0Gr6jpa6gIpbGOxuMwPP5ZMrMRG+O4X5iT0UHJ4Br1WvIfFI1E67o/wDYphF7/Zd9k3GPL8r+P33fdx265rKZtDc9A8Bxyw+DdHiuZVmnS3CySK24OwJBIPcE96Kg+Gvl/wDCv/D3kb/K+xps343Y5xnHGfpRVLYie7J/Hkk0Pg3WJbWJZbhLcvHGy7g7AggEdwT2riPDWsQ6xqOpnTzHLZR2NtiZYdhMrMDIme4X5cDooOBwBXafEKOCbwPrkd3N5Fs9qyyyhd2xSRlsd8DnFcpplraW+vak8DKlw2mWitAsBjAjVsRyc9S4ycfw4weQSYluXG3Kz0LT7xbyORghjMcjRlSyseDgNwTwRyO+DVqvPLDxHoVr42vLEarBYyJM+6DYyxz5Uc7ugYNv3Z9Qc4xW+3jXRf7es9Fjmml1K5meEQrCwMZUZLPnGFxyD3HI4qlJE8rOkoooqhCg4ozSUUCFpQabS5p3FYUmgHFJmjNO4WHZozTc0UXCw7PrSZpM0ZpXCwUZpKKB2FNJS0lIAoornE8aaM2v3mivNNHqVrKsTQtCxLlhkMmM5XHU9up4obsOzZtahdrZxRsULl5FjChgvUgE8kcDqa8z8S61Do+oaW1+UisJLG4zM0O8iZWJjTPYN82R0YDB4JrTv/Eug3njSzsf7Vt75mnUCDYzRwbVbnf0LFtmMehOcZqpqlraz69pjzsr3C6ZdqkLQGQGNmxJJx0KDBx/FnaMEg1nJ3LirbnX+A5JpvBujy3USxXD24aSNV2hGJJIA7AHtRTPh7HBD4H0OO0m8+2S1VYpSu3eoJAbHbI5xRVrYznuw+IYZvAuuqkAuXNqwWEgnzDkfLxzz045rhPCVhPpmtasNQuLnUEbTrNvtLIwdgMZiAPHyfnlsNz07z4hSSReB9ckgnFvKlqzJMW2iNgRhs9sda4bwhqt3rOsaqyW13CsdhaFbSbc77mYF5dvo5Oc9yCT3qJbmsb8r/rsXLzWdCur+G702f7V4i1AJAEt5wzSqMfI4JzEgI+cEf3gQTipPDVtYWfjG2muZSuszW82+YMy+fHEioVKEnEYGCpGDlGB71m+HPBmpaV4gGtztNd6hO0iXDPA6bUbbtKjaeV2judwJBx1roL/AMM2d34mTXms9Th1Jd26a2LDcSqquNy8AAEbSCDuOc0lcHZaJna2V1Be2yXFpKssL/ddehqauVs4Luyu2e1TUUti6Mbc24IKgYI6cZ4OQBjoOK2P7Slx/wAg29/74P8AhWlzM0qKzv7Sl/6Bl9/3wf8ACj+0pcZ/s29/74P+FFwNGis7+0pf+gZff9+z/hR/aUv/AEDb7/vg/wCFO4GjRWd/aUv/AEDL7/vg/wCFH9pS/wDQNvcf7h/wpXA0aKz/AO0pT/zDL7/vg/4Un9pS/wDQNvv++D/hRcDRorO/tKX/AKBl9/3wf8KBqUp/5ht7/wB8H/CncDRorO/tKXP/ACDL7/vg/wCFH9pS/wDQNvv++D/hRcDRpfrWb/aUv/QMvv8Avg/4Uf2lL/0Db3/vg/4UrgW7y6gsrZ7i7lWKFPvO3QV534mt7G98YXMlrJu1aG3hKzFmbyUlR0ChFIzGRksTk5dQOa6G9gur66Buk1JrYO7CEQBQARhR05xyckHPQ8Vm2Hhq0tfE8murZanNqTbds1yWO0hWVs7V5BBA2gADaMYqXqOLSM2z1nQ7S/mutSnNr4isFeApcThTCpz8iAHMsZJ+RQP7oABzVDxdYT6nrOkrYXFxYIunXjfaVRi6g5AiOOBv798rheeq+I/Bepap4gOtwNNaahA0aW7JA77413biw2jlix7jaAAM9af4v1S80fWNKLW93MJbC7LWkJZHLKxZJdvomM57EgjtUvbU0Vr6HbfDpWTwJoKvB9nZbRQYef3Zyfl5546c80U74fSSS+B9DkmnFzK9qrPMCSJGJOWyeuTzRWsVdGM92S+NbV73wjq9tHbS3TS25XyIvvyjIyq8jkjNeYeLdH1HUJVmsPD/AIg/tURRRJeiXZEsSqR5bLj5mAyN2MFiGG3oPaqMCpcblxlynj0Oix/2IyN4U8ULEJlY2puCblj5eNwlzgJn+HPJ+bp8pq+E9Dv4LlTq3h/XhdjdtuZZTNbrFswqGMYy44Xdj73zZPQ+1kDNGBS5B+0Z4KPD94NTWQeFfFo0kR4a1+3fvjNtP7wN/d7Yz15xWj4s0K+udp0jQdeF/sjAuY5DHAqhPmQxkHL4+Xdjlvm+XoPacD0oo5A9o9zx6PRY/wCwzGfCnigReeGNqbgm5J8vG4TZxszxsz1+bp8pq+FNDv4Jx/a3h/XvtmH23MshltxHswqGMYy+Pl3Y+982T0PtZAyeKMCjkD2jseCnw/ef2iHHhTxd/ZHl4Nr9u/fGbb/rN/8Ad7Yz15xV/wAWaFf3Mg/snQNeF9tjxdRSGKAIEwyGMg/Pj5d2OW+bjoPa8CjFHIP2rvc8eXRYzonlnwr4oEXn5+zG5P2nPl43+dnGzPGzPX5uny1W8K6HfQSgar4f177YA+LmWQyweWUwqeWMfPjC7scN82SOD7UQOlGKOQXtGeC/8I/ef2l5n/CKeLf7I8vH2X7d++87b/rN393tjPXnFaHi3Qr66lJ0rQdeF9hP9KikMUBjCYZPLOfnx8u7HLfNkdB7VgUYo5B+0d7nj39ixnRBH/winigRefn7N9pb7SD5eN/nZ+5n+DPXnp8tV/C2h31u5XVNA143gWTN1LIZYGQphU8sAfPj5d2OG+b5uh9pIGBxRijkF7Rs8F/4R68/tIyHwr4t/sjy8C1+3fvhNt+/u/udsZ684q94t0K/uZz/AGToGvC8wmbqKQxW5j8vDIIznD4yu7H3vmyOg9sxRijkH7V3uePPoyHQxGPCnigx+eSLUXBFyD5eN5mzjZn+DPX5uny1X8K6HfWxcapoOutfFJN13JIZYGQp8qLGAMPj5Q2OGG75uh9pwMUYo5Be0ex4L/wj95/aTOfCvi3+yDHhbX7d++WbaPnLf3e2MnnnFX/FuhX91cH+ytA14Xg25uopDFbtHswyCM5w5GV3Y+982QOB7XgelGKOQftHe549JosZ0MRjwp4oMfnsRai4IuQdmN5mzgpn+HPB56fKK/hTR9R06V5b7QNfOqmKWJ70yb4njZQPLVcfKSMDdjAYFju6H2nAxRgUcgvaO1jE8E2rWXhLSLaS2ltWitwvkS/fiGThW5PIGKK26KtaGctT/9k=";
    }
}