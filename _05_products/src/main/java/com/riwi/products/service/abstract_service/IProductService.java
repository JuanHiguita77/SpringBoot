package com.riwi.products.service.abstract_service;

import java.util.List;

import com.riwi.products.entities.Product;

//INTERFACE QUE SERÁ LA UNICA QUE SE MUESTRE DE CARA AL PUBLICO ANTES DE LA LOGICA EN SI
//SEGURIDAD PARA EVITAR VER EL CODIGO INTERNO
public interface IProductService 
{
    public Product save(Product product); 

    public List<Product> getAll();

    public Product findById(Long id);

    public void delete(Product product);

    public Product update(Long id, Product product); 

    public List<Product> search(String name);
}
