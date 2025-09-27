package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.CategoryRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.LoginResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.ProductRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll();
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

        HttpEntity<CategoryRequestDTO> categoryEntity = new HttpEntity<>(categoryDTO, headers);

        ResponseEntity<Object> categoryResponse =
                testRestTemplate.postForEntity("/categories", categoryEntity, Object.class);

        assertThat(categoryResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("test-product");
        productDTO.setDescription("Test Product");
        BigDecimal price = new BigDecimal("69");
        productDTO.setPrice(price);
        productDTO.setImageUrl("www.test.com");
        HttpEntity<ProductRequestDTO> productEntity = new HttpEntity<>(productDTO, headers);

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/products", productEntity, Object.class);

        assertThat(categoryResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}

