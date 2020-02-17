package ru.gds.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
@NamedEntityGraph(name = "books-entity-graph", attributeNodes = {@NamedAttributeNode("status")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE", length = 100000)
    private byte[] image;

    @JoinColumn(name = "STATUS", referencedColumnName = "ID", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_GENRE",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Set<Genre> genres;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Set<Author> authors;

    public Book(String name, Date createDate,
                String description, byte[] image,
                Set<Genre> genres, Set<Author> authors, Status status) {
        this.name = name;
        this.createDate = createDate;
        this.description = description;
        this.image = (image != null) ? image : new byte[]{};
        this.genres = (genres == null) ? new HashSet<Genre>() : genres;
        this.status = status;
        this.authors = (authors == null) ? new HashSet<Author>() : authors;
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
        return (image != null) ? image : new byte[]{};
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
