package co.fastcampus.command.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePhoneRequest {

    private String name;
    private String model;
    private String color;
    private Double price;
}
