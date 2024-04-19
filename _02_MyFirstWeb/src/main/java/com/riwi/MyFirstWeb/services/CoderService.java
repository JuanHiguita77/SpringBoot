package com.riwi.MyFirstWeb.services;

import java.util.List;

import com.riwi.MyFirstWeb.entity.Coder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.MyFirstWeb.repository.CoderRepository;

//Anteriormente conocido como modelo
@Service
public class CoderService 
{
    @Autowired//Inyeccion de dependencias(dependencia == objeto || entidad)
    private CoderRepository coderRepository;

    //Devuelve una lista de coders
    public List<Coder> findAll()
    {
        return this.coderRepository.findAll();
    }

    public Page<Coder> findAllPaginate(int page, int size)
    {
        //Validar que no sea menor a cero
        if (page < 0) 
        {
            page = 0;    
        }

        //Crear paginacion
        Pageable pageable = PageRequest.of(page, size);
        return this.coderRepository.findAll(pageable);
    }

    public Coder addCoder(Coder coder)
    {
        return this.coderRepository.save(coder);
    }

    //Mandar el coder encontrado para llenar el formulario
    public Coder findById(Long id)
    {
        Coder coder = this.coderRepository.findById(id).orElse(null);
        
        return coder;
    }

    //Le pasamos el id desde el controller
    public Coder updateCoder(Long id, Coder coder)
    {
        Coder coderFinded = this.findById(id);

        if(coderFinded == null) return null;
   
        return this.coderRepository.save(coder);
    }   

    public void delete(Long id)
    {
        this.coderRepository.deleteById(id);
    }
}
