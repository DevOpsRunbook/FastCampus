package co.fastcampus.orderManagementApp.controller;

import co.fastcampus.orderManagementApp.dto.SellerDto;
import co.fastcampus.orderManagementApp.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @PostMapping("/register/seller")
    public String registerAsSeller(@RequestBody SellerDto sellerDto) {
        return userRegistrationService.registerSeller(sellerDto);
    }

    @GetMapping("/sellerList")
    public List<SellerDto> getSellersList() {
        return userRegistrationService.getSellersList();
    }
}
