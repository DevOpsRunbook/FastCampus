package co.fastcampus.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@Getter
@Setter
@RedisHash("USER")
public class UserPostDto implements Serializable  {
    @JsonProperty("id")
    private int id;

    @Email
    @NotNull
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("password")
    private String password;

    @JsonProperty("surname")
    private String surname;
}
