package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.AddressDTO;
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
public class AddressController extends CrudController<Address, AddressDTO, AddressDTO, Long> {
    private final IAddressService addressService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public AddressController(IAddressService addressService, ModelMapper modelMapper, UserRepository userRepository) {
        super(Address.class, AddressDTO.class, AddressDTO.class);
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
    protected Address convertToEntity(AddressDTO dto) {
        Address address = getModelMapper().map(dto, Address.class);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getUserId()));

        address.setUser(user);
        return address;
    }
}
