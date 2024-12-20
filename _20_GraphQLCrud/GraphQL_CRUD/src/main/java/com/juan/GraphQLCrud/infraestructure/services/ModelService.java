package com.juan.GraphQLCrud.infraestructure.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juan.GraphQLCrud.api.dto.ModelDTO;
import com.juan.GraphQLCrud.domain.entities.Brand;
import com.juan.GraphQLCrud.domain.entities.Model;
import com.juan.GraphQLCrud.domain.repositories.BrandRepository;
import com.juan.GraphQLCrud.domain.repositories.ModelRepository;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IModelService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelService implements IModelService {
    
    private final ModelRepository modelRepository;

    private final BrandRepository brandRepository;

    @Override
    public Page<ModelDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Model> models = modelRepository.findAll(pageable);

        return models.map(model -> ModelDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build());
    }

    @Override
    public ModelDTO get(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(() -> new RuntimeException("Model no found!"));

        return ModelDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public ModelDTO create(ModelDTO request) {

        if(modelRepository.findByName(request.getName()) != null){
            throw new RuntimeException("Name already in use!");
        }

        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new RuntimeException("Brand no found!"));

        Model model = Model.builder()
                        .name(request.getName())
                        .brand(brand)
                        .build();

        modelRepository.save(model);

        return ModelDTO.builder()
                .id(model.getId())
                .name(request.getName())
                .brandId(brand.getId())
                .build();
    }

    @Override
    public ModelDTO update(ModelDTO request, Long id) {
        Model modelUpdated = modelRepository.findById(id).orElseThrow(() -> new RuntimeException("Model no found!"));

        if(modelRepository.findByName(request.getName()) != null && modelUpdated.getId() != id){
            throw new RuntimeException("Name already in use!");
        }

        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new RuntimeException("Brand no found!"));

        modelUpdated.setName(request.getName());
        modelUpdated.setBrand(brand);            

        modelUpdated = modelRepository.save(modelUpdated);

        return ModelDTO.builder()
                        .id(modelUpdated.getId())
                        .name(modelUpdated.getName())
                        .brandId(brand.getId())
                        .build();
    }

    @Override
    public void delete(Long id) {
        if(!modelRepository.existsById(id)){
            throw new IllegalArgumentException("Model no found with id: " + id);
        }

        modelRepository.deleteById(id);
    }

    @Override
    public Page<ModelDTO> findModelsByBrandId(Long brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Model> models = modelRepository.findModelsByBrandId(brandId, pageable);

        return models.map(model -> ModelDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .brandId(model.getBrand().getId())
                .build());
    }
 
    @PostConstruct
    public void init() {
        create(ModelDTO.builder().name("BMW 3 Series").brandId(1L).build());
        create(ModelDTO.builder().name("Audi A4").brandId(1L).build());
        create(ModelDTO.builder().name("Tesla Model S").brandId(4L).build());
        create(ModelDTO.builder().name("Ford Mustang").brandId(5L).build());
        create(ModelDTO.builder().name("Chevrolet Camaro").brandId(5L).build());
        create(ModelDTO.builder().name("Honda Civic").brandId(7L).build());
        create(ModelDTO.builder().name("Toyota Corolla").brandId(6L).build());
        create(ModelDTO.builder().name("Mercedes-Benz C-Class").brandId(4L).build());
        create(ModelDTO.builder().name("Nissan Altima").brandId(4L).build());
        create(ModelDTO.builder().name("Hyundai Sonata").brandId(2L).build());
    }
    
    
}
