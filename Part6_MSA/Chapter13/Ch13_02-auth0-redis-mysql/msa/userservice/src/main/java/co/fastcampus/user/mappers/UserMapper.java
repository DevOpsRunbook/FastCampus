package co.fastcampus.user.mappers;

import co.fastcampus.user.entities.User;
import co.fastcampus.user.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserGetDto userToUserGetDto(
            User user
    );

    User userPostDtoToUser(
            UserPostDto userPostDto
    );

    List<UserGetDto> userToUserGetDto(
            List<User> users
    );
}
