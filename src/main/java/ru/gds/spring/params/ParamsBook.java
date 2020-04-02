package ru.gds.spring.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.codec.multipart.FilePart;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ParamsBook {

    private String id;
    private String name;
    private String description;
    private FilePart file;
    private String statusId;
    private String genreIds;
    private String authorIds;

    public ParamsBook(String id, String name, String description, FilePart file,
                      String genreIds, String authorIds, String statusId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.file = file;
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

    public FilePart getFile() {
        return file;
    }

    public void setFile(FilePart file) {
        this.file = file;
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
