package ru.gds.spring.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ParamsBook {

    private long id;
    private String name;
    private String description;
    private MultipartFile file;
    private long statusId;
    private String genreIds;
    private String authorIds;

    public ParamsBook(long id, String name, String description, MultipartFile file,
                      String genreIds, String authorIds, long statusId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.file = file;
        this.statusId = statusId;
        this.genreIds = genreIds;
        this.authorIds = authorIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
    }

    public String getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(String authorIds) {
        this.authorIds = authorIds;
    }
}
