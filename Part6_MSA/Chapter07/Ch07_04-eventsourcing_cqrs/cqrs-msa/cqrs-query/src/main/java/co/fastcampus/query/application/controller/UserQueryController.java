package co.fastcampus.query.application.controller;

import co.fastcampus.query.application.dto.PhoneResponse;
import co.fastcampus.query.application.exception.FindPhoneException;
import co.fastcampus.query.domain.exception.PhoneNotFoundException;
import co.fastcampus.query.domain.service.FindPhoneService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class UserQueryController {

    @Autowired
    private FindPhoneService findPhoneService;

    @GetMapping("phone/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PhoneResponse findPhone(@PathVariable String name) {

        try{
            return findPhoneService.findByName(name);
        }catch (PhoneNotFoundException ex) {
            log.error(ex);
            throw new FindPhoneException();
        }
    }
}
