package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.AddressRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.AddressResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.updatedto.AddressUpdateDTO;
import br.edu.utfpr.pb.pw44s.server.model.Address;
import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.OrderItem;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.IAddressService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("addresses")
public class AddressController extends CrudController<Address, AddressRequestDTO, AddressResponseDTO, AddressUpdateDTO, Long> {
    private final IAddressService addressService;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    public AddressController(IAddressService addressService, ModelMapper modelMapper, AuthService authService) {
        super(Address.class, AddressRequestDTO.class, AddressResponseDTO.class, AddressUpdateDTO.class);
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    @Override
    protected ICrudService<Address, Long> getService() {
        return this.addressService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    @Override
    protected Address convertToEntity(AddressRequestDTO entityDto){
        Address address = super.convertToEntity(entityDto);

        User user = authService.getAuthenticatedUser();

        address.setUser(user);
        return address;
    }

    @Override
    @GetMapping //http://ip-api:port/request-mapping
    public ResponseEntity<List<AddressResponseDTO>> findAll() {
        return ResponseEntity.ok(
                addressService.findAllForAuthenticatedUser()
                        .stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }
}
