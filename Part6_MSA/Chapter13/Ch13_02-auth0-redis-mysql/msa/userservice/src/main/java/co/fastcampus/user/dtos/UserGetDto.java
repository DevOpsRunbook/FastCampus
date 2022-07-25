package co.fastcampus.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@Getter
@Setter
@RedisHash("USER")
public class UserGetDto implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    @JsonProperty("surname")
    private String surname;
}
