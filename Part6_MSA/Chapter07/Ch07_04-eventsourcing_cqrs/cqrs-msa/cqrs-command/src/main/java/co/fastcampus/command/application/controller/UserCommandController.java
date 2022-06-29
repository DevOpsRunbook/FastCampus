package co.fastcampus.command.application.controller;

import co.fastcampus.command.application.dto.CreatePhoneRequest;
import co.fastcampus.command.domain.service.CreateUserService;
import co.fastcampus.command.infrasturcture.eventsourcing.events.PhoneCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCommandController {

    @Autowired
    private CreateUserService createUserService;

    @PostMapping("phone")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PhoneCreatedEvent newPhone(@RequestBody  CreatePhoneRequest req) {
        return createUserService.create(req);
    }

}
