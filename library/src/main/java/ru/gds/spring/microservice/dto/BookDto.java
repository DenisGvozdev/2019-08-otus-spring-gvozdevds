package ru.gds.spring.microservice.dto;

import ru.gds.spring.microservice.domain.Author;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Genre;
import ru.gds.spring.microservice.domain.Status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class BookDto {

    private String id;
    private String name;
    private Date createDate;
    private String description;
    private byte[] image;
    private String imageExtension;
    private String imageString;
    private List<StatusDto> statuses;
    private List<GenreDto> genres;
    private List<AuthorDto> authors;

    public BookDto(){}

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public List<StatusDto> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusDto> statuses) {
        this.statuses = statuses;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public static BookDto toDtoLight(Book book) {
        if (book == null)
            return new BookDto();

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }

    public static List<BookDto> toListDto(Book book) {
        List<BookDto> list = new ArrayList<BookDto>();
        list.add(toDtoLight(book));
        return list;
    }

    public static BookDto toDto(Book book, List<Author> authors, List<Genre> genres, List<Status> statuses) {
        if (book == null)
            return new BookDto();

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());

        List<String> authorIds = new ArrayList<>();
        book.getAuthors().forEach((e) -> authorIds.add(e.getId()));
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authors.forEach(author -> authorDtoList.add(AuthorDto.toDtoWithSelect(author, authorIds)));
        bookDto.setAuthors(authorDtoList);

        List<String> genreIds = new ArrayList<>();
        book.getGenres().forEach((e) -> genreIds.add(e.getId()));
        List<GenreDto> genreDtoList = new ArrayList<>();
        genres.forEach(genre -> genreDtoList.add(GenreDto.toDtoWithSelect(genre, genreIds)));
        bookDto.setGenres(genreDtoList);

        List<StatusDto> statusDtoList = new ArrayList<>();
        statuses.forEach(status -> statusDtoList.add(StatusDto.toDtoWithSelect(status, book.getStatus().getId())));
        bookDto.setStatuses(statusDtoList);

        String encodedImage = null;
        try {
            String base64SignatureImage = Base64.getEncoder().encodeToString(book.getImage());
            encodedImage = URLEncoder.encode(base64SignatureImage, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String extension = "data:image/jpg;base64,";
        bookDto.setImage(book.getImage());
        bookDto.setImageExtension(extension);
        bookDto.setImageString(extension + encodedImage);

        return bookDto;
    }
}
