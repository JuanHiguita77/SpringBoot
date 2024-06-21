package com.riwi.mapstruct.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import com.riwi.mapstruct.dto.GetProduct;
import com.riwi.mapstruct.entity.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryMapper.class})
public interface ProductMapper 
{
    //Util cuando los atributos del DTO y la entidad son diferentes
    @Mappings({
        //source = id de la entidad -------- target = id del DTO
        /*@Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "creationDate", target = "creationDate")

        ENTIDAD -- DTO
        @Mappipng(source = "id", target = "id")*/
    })
    GetProduct productGet(Product product);

    @InheritInverseConfiguration
    //Sirve para ignorar una propiedad en especifico
    /*@Mappings({
        @Mapping(target = "creationDate", ignore = true)
    })*/
    Product DTOtoEntity(GetProduct getProduct);

    List<GetProduct> toGetproductList(List<Product> productList);

    List<Product> toProductList(List<GetProduct> getProductList);

}
