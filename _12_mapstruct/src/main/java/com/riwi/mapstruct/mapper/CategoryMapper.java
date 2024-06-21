package com.riwi.mapstruct.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.riwi.mapstruct.dto.GetCategory;
import com.riwi.mapstruct.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper 
{
    //No necesita mappers ya que se llaman igual en el DTO y la entidad
    GetCategory toGetCategory(Category category);

    @InheritInverseConfiguration
    Category DTOtoEntity(GetCategory getCategory);

    List<GetCategory> toGetCategoryList(List<Category> categoryList);

    List<Category> DTOtoEntityList(List<GetCategory> getCategoriesList);
}
