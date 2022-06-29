package co.fastcampus.jpaservice.mapper.common;

import java.util.List;

public interface EntityMapper<E,D> {

    E toEntity(D dto);

    D toDto(E entiy);

    List<E> toEntity(List<D> dtos);

    List<D> toDto(List<E> entities);
}
