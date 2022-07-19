package co.fastcampus.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.fastcampus.service.AwsKmsService;

@RestController
public class AwsKmsController {

    @Autowired
    private AwsKmsService awsKmsService;

    @RequestMapping(value = "/kms/encrypt/{stringToEncrypt}", method = RequestMethod.GET)
    public String encryptThroughKms(@PathVariable String stringToEncrypt) throws UnsupportedEncodingException {
        String encryptedString = awsKmsService.encrypt(stringToEncrypt);
        return URLEncoder.encode(encryptedString, "UTF-8");
    }

    @RequestMapping(value = "/kms/decrypt", method = RequestMethod.GET)
    public String decryptThroughKms(@RequestParam String encryptedString) {
        return awsKmsService.decrypt(encryptedString);
    }
}