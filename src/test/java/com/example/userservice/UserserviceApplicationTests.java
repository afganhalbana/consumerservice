package com.example.userservice;

import com.google.gson.Gson;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("application-test")
@AutoConfigureStubRunner(ids = {"com.example:tempratureservice:0.0.1-SNAPSHOT:8082"})
// @AutoConfigureWireMock(port = 8081)
class UserserviceApplicationTests {
  @Value("${temperature.api.stubs.artifact}")
  private String temperatureStubArtifact;

  @Value("${temperature.api.stubs.version}")
  private String temperatureStubVersion;

  @Autowired
  private TemperatureListener temperatureListener;

  @Autowired
  private StubTrigger stubTrigger;

  /*@Value("${temperature.api.stubs.version}")
  public void setTemperatureStubVersion(String name) {
    temperatureStubArtifact = name;
  }

  @Value("${temperature.api.stubs.artifact}")
  private void setTemperatureStubArtifact(String name) {
    temperatureStubVersion = name;
  }*/

  private static Gson gson;
  private static String json;
  private static TempratureDTO tempratureDTO;

  /*@RegisterExtension
  static StubRunnerExtension stubRunnerExtension = new StubRunnerExtension()
      .downloadStub("com.example:tempratureservice:0.0.1-SNAPSHOT").withPort(8082)
      .withMappingsOutputFolder("target/outputmappingsforrule");*/

  @BeforeAll
  static void contextLoads() {
    tempratureDTO = TempratureDTO.builder().unitName("C").temprature(1).build();
    gson = new Gson();
    json = gson.toJson(tempratureDTO);
  }

  @Test
  public void test_should_return_temperature() {
    /* WireMock.stubFor(
        WireMock.get(WireMock.urlEqualTo("/temperature"))
            .willReturn(WireMock.aResponse().withBody(json).withStatus(201)));
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity("http://localhost:8081/temperature", String.class);
    then(responseEntity.getStatusCodeValue()).isEqualTo(201);
    then(responseEntity.getBody()).isEqualTo(json);*/
  }

  @Test
  public void testShouldReturnTemperatureIntegration() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> entity =
        restTemplate.getForEntity("http://localhost:8082/temperature?type=C", String.class);
    BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(201);
    BDDAssertions.then(entity.getBody()).isEqualTo(json);
  }

  @Test
  public void testShouldReturnTemperatureIntegrationMsg() {
    String triggeredBy = "triggerTemperature";
    String name = "cantik";
    stubTrigger.trigger(triggeredBy);
    BDDAssertions.then(this.temperatureListener.name).isEqualTo(name);
  }
}
