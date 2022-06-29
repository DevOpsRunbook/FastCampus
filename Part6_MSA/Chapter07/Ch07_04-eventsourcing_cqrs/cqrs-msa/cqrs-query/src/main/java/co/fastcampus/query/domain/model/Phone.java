package co.fastcampus.query.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Phone {

    @Id
    @JsonIgnore
    private String id;
    private String name;
    private String model;
    private String color;
    private Double price;
    private String creationDate;

    public Phone(){ }
}
