package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.LoginResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.*;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.User;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postOrder_whenUserLogged_receiveCreated() {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("test-category");

        HttpEntity<CategoryRequestDTO> categoryEntity = new HttpEntity<>(categoryDTO, headers);
        ResponseEntity<Object> categoryResponse =
                testRestTemplate.postForEntity("/categories", categoryEntity, Object.class);

        assertThat(categoryResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("test-product");
        productDTO.setDescription("Test Product");
        productDTO.setPrice(new BigDecimal("69"));
        productDTO.setImageUrl("www.test.com");
        productDTO.setCategory(1L); // ou recuperar dinamicamente se a API devolver id

        HttpEntity<ProductRequestDTO> productEntity = new HttpEntity<>(productDTO, headers);
        ResponseEntity<Map> productResponse =
                testRestTemplate.postForEntity("/products", productEntity, Map.class);

        assertThat(productResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long productId = ((Number) productResponse.getBody().get("id")).longValue();

        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO();
        itemDTO.setProduct(productId);
        itemDTO.setQuantity(2);
        itemDTO.setUnitPrice(new BigDecimal("70"));

        OrderRequestDTO orderDTO = new OrderRequestDTO();

        HttpEntity<OrderRequestDTO> entity = new HttpEntity<>(orderDTO, headers);

        ResponseEntity<OrderResponseDTO> orderResponse =
                testRestTemplate.postForEntity("/orders", entity, OrderResponseDTO.class);

        assertThat(orderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
