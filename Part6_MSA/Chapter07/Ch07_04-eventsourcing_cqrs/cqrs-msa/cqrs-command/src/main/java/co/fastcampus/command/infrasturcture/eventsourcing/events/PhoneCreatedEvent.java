package co.fastcampus.command.infrasturcture.eventsourcing.events;

import co.fastcampus.command.domain.model.Phone;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PhoneCreatedEvent {
    private UUID uuid;
    private Phone phone;
}
