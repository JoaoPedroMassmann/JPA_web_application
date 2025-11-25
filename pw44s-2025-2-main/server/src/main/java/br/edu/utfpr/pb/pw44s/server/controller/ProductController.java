package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.CategoryRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.ProductRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.ProductUpdateDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.ProductResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IProductService;
import br.edu.utfpr.pb.pw44s.server.service.impl.CategoryServiceImpl;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends CrudController<Product, ProductRequestDTO, ProductResponseDTO, ProductUpdateDTO, Long> {
    private final IProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryServiceImpl categoryService;

    public ProductController(IProductService productService, ModelMapper modelMapper, CategoryServiceImpl categoryService) {
        super(Product.class, ProductRequestDTO.class, ProductResponseDTO.class, ProductUpdateDTO.class);
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    protected Product convertToEntity(ProductRequestDTO entityDto) {
        if(productService.existsByName(entityDto.getName().trim())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with name \"" + entityDto.getName() + "\" already exists");
        }

        Category category = categoryService.findById(entityDto.getCategory());
        if(category == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        Product product = super.convertToEntity(entityDto);
        product.setCategory(category);

        return product;
    }

    @Override
    protected ProductResponseDTO convertToDto(Product entity) {
        ProductResponseDTO productDTO = super.convertToDto(entity);
        if(entity.getCategory() != null) {
            productDTO.setCategoryId(entity.getCategory().getId());
            productDTO.setCategoryName(entity.getCategory().getName());
        }
        return productDTO;
    }

    @Override
    protected Product convertToUpdatedEntity(Product existingEntity, ProductUpdateDTO updateDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        if (updateDto.getCategory() != null) {
            Category category = categoryService.findById(updateDto.getCategory());

            if(category == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
            }
            existingEntity.setCategory(category);
        }

        mapper.map(updateDto, existingEntity);
        return existingEntity;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllByCategoryId(@PathVariable Long id) {
        try {
            List<Product> products = productService.findAllByCategoryId(id);

            List<ProductResponseDTO> dtoList = products.stream()
                    .map(this::convertToDto)
                    .toList();

            return ResponseEntity.ok(dtoList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar produtos por categoria");
        }
    }



    @Override
    protected ICrudService<Product, Long> getService() {
        return productService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }
}