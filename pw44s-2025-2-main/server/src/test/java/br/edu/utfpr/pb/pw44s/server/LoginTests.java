package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.server.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderItemRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderRepository;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void cleanup() {
        orderItemRepository.deleteAll();
        orderItemRepository.flush();

        orderRepository.deleteAll();
        orderRepository.flush();

        addressRepository.deleteAll();
        addressRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    //methodName_condition_expectedBehaviour
    @Test
    public void login_whenCredentialsAreValid_receiveToken() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-user@email.com");

        ResponseEntity<Object> registerResponse =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var loginPayload = Map.of("username","test-user","password","P4ssword");

        ResponseEntity<String> loginResponse =
                testRestTemplate.postForEntity("/login", loginPayload, String.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        String body = loginResponse.getBody();
        assertThat(body).isNotNull();
        assertThat(body).contains("token");
    }

    @Test
    public void login_whenPasswordIsInvalid_receiveUnauthorized() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("Test");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test@email.com");
        testRestTemplate.postForEntity("/users", userDTO, Object.class);

        var loginPayload = Map.of("username","test-user","password","a");

        ResponseEntity<String> response =
                testRestTemplate.postForEntity("/login", loginPayload, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void login_whenUserDoesNotExist_receiveUnauthorized() {
        var loginPayload = Map.of("username","test-user","password","P4ssword");
        ResponseEntity<String> response =
                testRestTemplate.postForEntity("/login", loginPayload, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
