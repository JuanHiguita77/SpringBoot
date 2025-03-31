package com.juan.GraphQLCrud.infraestructure.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juan.GraphQLCrud.api.dto.BrandDTO;
import com.juan.GraphQLCrud.api.dto.ModelDTO;
import com.juan.GraphQLCrud.domain.entities.Brand;
import com.juan.GraphQLCrud.domain.repositories.BrandRepository;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IBrandService;
import com.juan.GraphQLCrud.util.enums.Country;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService{
    
    private final BrandRepository brandRepository;

    @Override
    public Page<BrandDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Brand> brands = brandRepository.findAll(pageable);

        return brands.map(brand -> BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .country(brand.getCountry())
                .models(brand.getModels().stream()
                .map(model -> ModelDTO.builder()
                    .id(model.getId())
                    .name(model.getName())
                    .build())
                .toList())
                .build());
    }

    @Override
    public BrandDTO get(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand no found!"));

        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .country(brand.getCountry())
                .models(brand.getModels().stream()
                .map(model -> ModelDTO.builder()
                    .id(model.getId())
                    .name(model.getName())
                    .build())
                .toList())
                .build();
    }

    @Override
    public BrandDTO create(BrandDTO request) {

        Brand brand = brandRepository.findByName(request.getName());

        if(brand != null)
        {
            throw new RuntimeException("Brand already exists!");
        }

        brand = Brand.builder()
                        .name(request.getName())
                        .country(request.getCountry())
                        .build();

        brand = brandRepository.save(brand);

        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .country(brand.getCountry())
                .build();
    }

    @Override
    public BrandDTO update(BrandDTO request, Long id) {

        Brand brandUpdated = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand no found!"));

        Brand brand = brandRepository.findByName(request.getName());

        if(brand != null && brand.getId() != id)
        {
            throw new RuntimeException("Name already in use!");
        }

        brandUpdated.setName(request.getName());
        brandUpdated.setCountry(request.getCountry());
        
        brandRepository.save(brandUpdated);
        
        return BrandDTO.builder()
                        .id(brandUpdated.getId())
                        .name(brandUpdated.getName())
                        .country(brandUpdated.getCountry())
                        .build();
    }

    @Override
    public void delete(Long id) {
        if(!brandRepository.existsById(id)){
            throw new IllegalArgumentException("Brand no found with id: " + id);
        }

        brandRepository.deleteById(id);
    }

    @Override
    public Flux<BrandDTO> getAllBrandsFlux() {
        return Flux.fromIterable(brandRepository.findAll()) // Devuelve todos los elementos
                   .map(brand -> BrandDTO.builder()
                                        .id(brand.getId())
                                        .name(brand.getName())
                                        .country(brand.getCountry())
                                        .models(brand.getModels().stream()
                                                    .map(model -> ModelDTO.builder()
                                                                          .id(model.getId())
                                                                          .name(model.getName())
                                                                          .build())
                                                    .collect(Collectors.toList()))
                                        .build())
                   .delayElements(Duration.ofSeconds(1))  // Simula un retraso entre los elementos
                   .take(10);  // Limita a 10 elementos emitidos
    }

    @Override
    public Mono<BrandDTO> getOneMono(Long id)
    {
        return Mono.justOrEmpty(brandRepository.findById(id))
            .map(brand -> BrandDTO.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .country(brand.getCountry())
                    .models(brand.getModels().stream()
                            .map(model -> ModelDTO.builder()
                                    .id(model.getId())
                                    .name(model.getName())
                                    .build())
                            .toList())
                    .build())
            .switchIfEmpty(Mono.error(new RuntimeException("Brand not found with id: " + id))); 
    }


    /* @PostConstruct
   private void init() {
        create(BrandDTO.builder()
                .name("BMW")
                .country(Country.GER)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Ferrari")
                .country(Country.ES)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Toyota")
                .country(Country.JP)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Ford")
                .country(Country.US)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Audi")
                .country(Country.GER)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Honda")
                .country(Country.JP)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Chevrolet")
                .country(Country.US)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Renault")
                .country(Country.FR)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Hyundai")
                .country(Country.KR)
                .models(new ArrayList<>())
                .build());

        create(BrandDTO.builder()
                .name("Tesla")
                .country(Country.US)
                .models(new ArrayList<>())
                .build());
    }
*/


}
