package co.fastcampus.orderManagementApp.service;

import co.fastcampus.orderManagementApp.dto.SellerDto;

import java.util.List;


public interface UserRegistrationService {
    String registerSeller(SellerDto sellerDto);

    List<SellerDto> getSellersList();
}
