package co.fastcampus.service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.util.Base64;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
@Service
public class AwsKmsService {

    @Autowired
    private AWSKMSClient kmsBean;

    @Value("${cloud.aws.kms.org.master.key.id}")
    private String awsKmsOrgCMKId;

    public void listKeys() {
        System.out.println("List of KMS Master Keys: " + kmsBean.listKeys());
    }

    public String encrypt(String stringToEncrypt) {
        EncryptRequest encryptRequest = new EncryptRequest();
        encryptRequest.setKeyId(awsKmsOrgCMKId);
        encryptRequest.setPlaintext(ByteBuffer.wrap(stringToEncrypt.getBytes()));
        EncryptResult encryptResult = kmsBean.encrypt(encryptRequest);
        ByteBuffer byteBuffer = encryptResult.getCiphertextBlob();
        return Base64.encodeAsString(byteBuffer.array());
    }

    public String decrypt(String encryptedString) {
        DecryptRequest decryptRequest = new DecryptRequest();
        decryptRequest.setCiphertextBlob(ByteBuffer.wrap(Base64.decode(encryptedString)));
        DecryptResult decryptedResult =  kmsBean.decrypt(decryptRequest);
        return StandardCharsets.UTF_8.decode(decryptedResult.getPlaintext()).toString();
    }
}