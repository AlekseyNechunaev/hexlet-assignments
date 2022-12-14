package exercise.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

}
