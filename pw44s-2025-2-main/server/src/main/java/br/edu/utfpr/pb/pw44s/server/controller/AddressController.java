package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.AddressRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.AddressResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.Address;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.server.service.IAddressService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addresses")
public class AddressController extends CrudController<Address, AddressRequestDTO, AddressResponseDTO, Long> {
    private final IAddressService addressService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public AddressController(IAddressService addressService, ModelMapper modelMapper, UserRepository userRepository) {
        super(Address.class, AddressRequestDTO.class, AddressResponseDTO.class);
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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
    protected Address convertToEntity(AddressRequestDTO dto) {
        Address address = getModelMapper().map(dto, Address.class);

        User user = userRepository.findById(dto.getUser())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getUser()));

        address.setUser(user);
        return address;
    }

    @Override
    protected AddressResponseDTO convertToDto(Address entity) {
        AddressResponseDTO addressDTO = super.convertToDto(entity);
        if(entity.getUser() != null) {
            addressDTO.setUserId(entity.getUser().getId());
            addressDTO.setUsername(entity.getUser().getUsername());
        }
        return addressDTO;
    }
}
