package co.fastcampus.registrationService.service;

import co.fastcampus.registrationService.dto.SellerDto;

import java.util.List;

public interface RegistrationService {


    String addSeller(SellerDto sellerDto);

    List<SellerDto> getSellersList();
}
