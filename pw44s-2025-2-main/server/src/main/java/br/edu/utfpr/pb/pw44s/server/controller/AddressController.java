package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.AddressRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.AddressResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.updatedto.AddressUpdateDTO;
import br.edu.utfpr.pb.pw44s.server.model.Address;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.IAddressService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private User currentUserOrNull() {
        try {
            return authService.getAuthenticatedUser();
        } catch (Exception ex) {
            return null;
        }
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
        User user = currentUserOrNull();
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You must be logged in to register an address.");
        }
        Address address = super.convertToEntity(entityDto);

        address.setUser(user);
        return address;
    }

    /*@Override
    @GetMapping //http://ip-api:port/request-mapping
    public ResponseEntity<List<AddressResponseDTO>> findAll() {
        return ResponseEntity.ok(
                addressService.findAllForAuthenticatedUser()
                        .stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }*/

    @Override
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> findAll() {
        User user = currentUserOrNull();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User must be authenticated.");
        }

        return ResponseEntity.ok().body(addressService.findAllForAuthenticatedUser()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AddressResponseDTO> update(@PathVariable Long id, AddressUpdateDTO entityDto){
        User user = currentUserOrNull();

        Address address= addressService.findById(id);
        if(!address.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You canoot update another user's address.");
        }

        modelMapper.map(entityDto, address);
        Address updatedAddress = addressService.save(address);

        return ResponseEntity.ok().body(convertToDto(updatedAddress));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id){
        User user = currentUserOrNull();

        Address address = addressService.findById(id);
        if(!address.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You canoot delete another user's address.");
        }

        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
