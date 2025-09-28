package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.CategoryRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.CategoryResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.service.ICategoryService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
public class CategoryController extends CrudController<Category, CategoryRequestDTO, CategoryResponseDTO, Long> {
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(ICategoryService categoryService, ModelMapper modelMapper) {
        super(Category.class, CategoryRequestDTO.class, CategoryResponseDTO.class);
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
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
