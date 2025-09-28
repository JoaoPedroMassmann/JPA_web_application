package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.CategoryRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.LoginResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.server.repository.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

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

        productRepository.deleteAll();
        productRepository.flush();

        categoryRepository.deleteAll();
        categoryRepository.flush();
    }

    //methodName_condition_expectedBehaviour
    @Test
    public void postCategory_whenUserLogged_receiveCreated() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");
        testRestTemplate.postForEntity("/users", userDTO, Object.class);

        var loginPayload = new java.util.HashMap<String, String>();
        loginPayload.put("username", "test-user");
        loginPayload.put("password", "P4ssword");

        ResponseEntity<LoginResponseDTO> loginResponse =
                testRestTemplate.postForEntity("/login", loginPayload, LoginResponseDTO.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = loginResponse.getBody().getToken();

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("test-category");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<CategoryRequestDTO> entity = new HttpEntity<>(categoryDTO, headers);

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/categories", entity, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void postCategory_whenUserNotLogged_receiveUnauthorized() {
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("unauth-category");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/categories", categoryDTO, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postCategory_whenInvalidData_receiveBadRequest() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");
        testRestTemplate.postForEntity("/users", userDTO, Object.class);

        var loginPayload = new java.util.HashMap<String, String>();
        loginPayload.put("username", "test-user");
        loginPayload.put("password", "P4ssword");

        ResponseEntity<LoginResponseDTO> loginResponse =
                testRestTemplate.postForEntity("/login", loginPayload, LoginResponseDTO.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = loginResponse.getBody().getToken();

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<CategoryRequestDTO> entity = new HttpEntity<>(categoryDTO, headers);
        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/categories", entity, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}

