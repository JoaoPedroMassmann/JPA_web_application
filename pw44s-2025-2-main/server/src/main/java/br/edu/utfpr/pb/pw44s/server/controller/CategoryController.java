package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.CategoryRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.CategoryResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.updatedto.CategoryUpdateDTO;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.service.ICategoryService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("categories")
public class CategoryController extends CrudController<Category, CategoryRequestDTO, CategoryResponseDTO, CategoryUpdateDTO, Long> {
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(ICategoryService categoryService, ModelMapper modelMapper) {
        super(Category.class, CategoryRequestDTO.class, CategoryResponseDTO.class, CategoryUpdateDTO.class);
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    protected Category convertToEntity(CategoryRequestDTO entityDto) {
        if(categoryService.existsByName(entityDto.getName().trim())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with name \"" + entityDto.getName() + "\" already exists");
        }
        Category category = super.convertToEntity(entityDto);
        return category;
    }

    @Override
    protected ICrudService<Category, Long> getService() {
        return this.categoryService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
