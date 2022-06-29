package co.fastcampus.registrationService.controller;

import co.fastcampus.registrationService.dto.SellerDto;
import co.fastcampus.registrationService.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/addSeller")
    public String addSeller(@RequestBody SellerDto sellerDto){
        return registrationService.addSeller(sellerDto);
    }

    @GetMapping("/sellersList")
    public List<SellerDto> getSellersList() {
        return registrationService.getSellersList();
    }
}
