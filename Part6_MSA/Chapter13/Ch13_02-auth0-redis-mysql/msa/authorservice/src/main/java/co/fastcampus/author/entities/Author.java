package co.fastcampus.author.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Basic
    @Column(name = "birth_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "author_book",
//            joinColumns = @JoinColumn(name = "author_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
}
