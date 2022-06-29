package co.fastcampus.query.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneResponse {

    private String color;
    private String model;
    private String name;
    private Double price;
}
