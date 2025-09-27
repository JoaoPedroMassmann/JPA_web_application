package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
                SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

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
    public void postUser_whenUserIsValid_receiveOK() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void putUser_whenUserIsValid_userSavedToDatabase() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);

        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void putUser_whenUserIsValid_passwordIsHashedInDatabase() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername("test-user");
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");

        testRestTemplate.postForEntity("/users", userDTO, Object.class);

        List<User> users = userRepository.findAll();
        User userDB =  users.get(0);

        assertThat(userDTO.getPassword()).isNotEqualTo(userDB.getPassword());
    }

    @Test
    public void putUser_whenUserHasNullUsername_receiveBadRequest() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername( null );
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void putUser_whenUserHasUsernameWithLessThanRequired_receiveBadRequest() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername( "abc" );
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email@email.com");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void putUser_whenUserHasUsernameWithWrongEmail_receiveBadRequest() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setUsername( "test-user" );
        userDTO.setDisplayName("test-Display");
        userDTO.setPassword("P4ssword");
        userDTO.setEmail("test-email");

        ResponseEntity<Object> response =
                testRestTemplate.postForEntity("/users", userDTO, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
