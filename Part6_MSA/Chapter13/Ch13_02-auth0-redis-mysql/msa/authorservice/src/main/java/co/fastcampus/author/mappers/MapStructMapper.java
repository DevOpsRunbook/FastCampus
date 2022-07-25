package co.fastcampus.author.mapstruct.mappers;

import co.fastcampus.author.entities.Author;
import co.fastcampus.author.mapstruct.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    AuthorDto authorToAuthorDto(
            Author author
    );

    AuthorAllDto authorToAuthorAllDto(
            Author author
    );

    List<AuthorAllDto> authorsToAuthorAllDtos(
            List<Author> authors
    );

}