package ru.gds.spring.dto;

import ru.gds.spring.domain.Book;
import ru.gds.spring.util.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<BookDto> toListDto(Book book) {
        List<BookDto> list = new ArrayList<BookDto>();
        list.add(toDtoLight(book));
        return list;
    }

    public static BookDto toDtoLight(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }

    public static List<BookDto> toListDtoWithImage(Book book) {
        List<BookDto> list = new ArrayList<BookDto>();
        list.add(toDtoWithImage(book));
        return list;
    }

    public static BookDto toDto(Book book) {
        if(book == null)
            return null;

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());

        List<AuthorDto> authorDtoList = book.getAuthors().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList());
        bookDto.setAuthors(authorDtoList);

        List<GenreDto> genreDtoList = book.getGenres().stream().map(GenreDto::toDto)
                .collect(Collectors.toList());
        bookDto.setGenres(genreDtoList);

        List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
        statusDtoList.add(StatusDto.toDto(book.getStatus()));
        bookDto.setStatuses(statusDtoList);
        bookDto.setImage(book.getImage());
        bookDto.setImageExtension("data:image/jpg;base64,");
        bookDto.setImageString(CommonUtils.bytesToString(book.getImage()));

        return bookDto;
    }

    private static BookDto toDtoWithImage(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());
        bookDto.setImageString(CommonUtils.bytesToString(book.getImage()));
        return bookDto;
    }
}
