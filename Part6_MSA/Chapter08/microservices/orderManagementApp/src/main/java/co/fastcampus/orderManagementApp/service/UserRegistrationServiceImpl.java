package co.fastcampus.orderManagementApp.service;

import co.fastcampus.orderManagementApp.dto.SellerDto;
import co.fastcampus.orderManagementApp.service.resilience4j.UserRegistrationResilience4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
    private UserRegistrationResilience4j userRegistrationResilience4j;


    public UserRegistrationServiceImpl(UserRegistrationResilience4j userRegistrationResilience4j) {
        this.userRegistrationResilience4j = userRegistrationResilience4j;
    }

    @Override
    public String registerSeller(SellerDto sellerDto) {

        String registerSeller = null;


        long start = System.currentTimeMillis();

        registerSeller = userRegistrationResilience4j.registerSeller(sellerDto);

        logger.info("add seller call returned in - {}", System.currentTimeMillis() - start);

        return registerSeller;

    }

    @Override
    public List<SellerDto> getSellersList() {
        return userRegistrationResilience4j.getSellersList();
    }
}
