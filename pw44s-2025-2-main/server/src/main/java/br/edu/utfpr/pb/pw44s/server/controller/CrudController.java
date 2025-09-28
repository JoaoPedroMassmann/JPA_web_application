package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

// T = class type, D = dto type, ID = attribute related to primary key type  
public abstract class CrudController<T, ReqDTO, ResDTO, ID extends Serializable> {

    protected abstract ICrudService<T, ID> getService();
    protected abstract ModelMapper getModelMapper();

    private final Class<T> typeClass;
    private final Class<ReqDTO> reqDtoClass;
    private final Class<ResDTO> resDtoClass;


    public CrudController(Class<T> typeClass, Class<ReqDTO> reqDtoClass, Class<ResDTO> resDtoClass) {
        this.typeClass = typeClass;
        this.reqDtoClass = reqDtoClass;
        this.resDtoClass = resDtoClass;
    }

    protected ResDTO convertToDto(T entity) {
        return getModelMapper().map(entity, this.resDtoClass);
    }

    protected T convertToEntity(ReqDTO entityDto) {
        return getModelMapper().map(entityDto, this.typeClass);
    }

    @GetMapping //http://ip-api:port/request-mapping  
    public ResponseEntity<List<ResDTO>> findAll() {
        return ResponseEntity.ok(
                getService().findAll()
                        .stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("page") //http://ip-api:port/request-mapping/page?page=1&size=5  
    public ResponseEntity<Page<ResDTO>> findAll(@RequestParam int page,
                                           @RequestParam int size,
                                           @RequestParam(required = false) String order,
                                           @RequestParam(required = false) Boolean asc) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(getService().findAll(pageRequest).map(this::convertToDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResDTO> findOne(@PathVariable ID id) {
        T entity = getService().findById(id);
        if (entity != null) {
            return ResponseEntity.ok(convertToDto(entity));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<ResDTO> create(@RequestBody @Valid ReqDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(getService().save(convertToEntity(entity))));

    }

    @PutMapping("{id}")
    public ResponseEntity<ResDTO> update(@PathVariable ID id, @RequestBody @Valid ReqDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(getService().save(convertToEntity(entity))));
    }

    @GetMapping("exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable ID id) {
        return ResponseEntity.ok(getService().exists(id));
    }

    @GetMapping("count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(getService().count());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }

} 