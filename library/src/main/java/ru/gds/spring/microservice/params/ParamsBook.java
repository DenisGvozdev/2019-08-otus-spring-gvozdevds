package ru.gds.spring.microservice.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ParamsBook {

    private String id;
    private String name;
    private String description;
    private MultipartFile fileTitle;
    private MultipartFile fileContent;
    private String statusId;
    private String genreIds;
    private String authorIds;

    public ParamsBook(String id, String name, String description, MultipartFile fileTitle,
                      MultipartFile fileContent, String genreIds, String authorIds, String statusId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileTitle = fileTitle;
        this.fileContent = fileContent;
        this.statusId = statusId;
        this.genreIds = genreIds;
        this.authorIds = authorIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public MultipartFile getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(MultipartFile fileTitle) {
        this.fileTitle = fileTitle;
    }

    public MultipartFile getFileContent() {
        return fileContent;
    }

    public void setFileContent(MultipartFile fileContent) {
        this.fileContent = fileContent;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
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
