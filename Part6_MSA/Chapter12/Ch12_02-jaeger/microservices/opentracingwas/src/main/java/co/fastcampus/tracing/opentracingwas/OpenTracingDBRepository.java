package co.fastcampus.tracing.opentracingwas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenTracingDBRepository extends JpaRepository<OpenTracingEntity, Long> {
}