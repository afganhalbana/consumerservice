package com.example.userservice;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

  RestTemplate restTemplate;

  @GetMapping(value = "is_cold")
  public ResponseEntity<ResponseDTO> get() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
    restTemplate = new RestTemplate();
    ResponseEntity<TempratureDTO> responseEntity =
    restTemplate.exchange("http://localhost:8081/temperature?type=c", HttpMethod.GET, httpEntity,
        TempratureDTO.class);
    TempratureDTO result = responseEntity.getBody();
    ResponseDTO responseDTO = ResponseDTO.builder().isCold(false).build();
    if (result.getTemprature() > 32) {
      responseDTO.setCold(true);
    }
    return new ResponseEntity<>(responseDTO,HttpStatus.OK);
  }

}
