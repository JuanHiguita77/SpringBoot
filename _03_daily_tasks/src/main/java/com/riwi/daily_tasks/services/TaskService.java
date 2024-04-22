package com.riwi.daily_tasks.services;

import java.util.List;

import com.riwi.daily_tasks.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.daily_tasks.repository.TaskRepository;

//Anteriormente conocido como modelo
@Service
public class TaskService 
{
    @Autowired//Inyeccion de dependencias(dependencia == objeto || entidad)
    private TaskRepository TaskRepository;

    //Devuelve una lista de Tasks
    public List<Task> findAll()
    {
        return this.TaskRepository.findAll();
    }

    public Page<Task> findAllPaginate(int page, int size)
    {
        //Validar que no sea menor a cero
        if (page < 0) 
        {
            page = 0;    
        }

        //Crear paginacion
        Pageable pageable = PageRequest.of(page, size);
        return this.TaskRepository.findAll(pageable);
    }

    public Task addTask(Task Task)
    {
        return this.TaskRepository.save(Task);
    }

    //Encontrar por nombre
    public List<Task> findByTitle(String taskName)
    {
        List<Task> tasks = this.TaskRepository.findByTitle(taskName);

        return tasks;
    }

    //Mandar el Task encontrado para llenar el formulario
    public Task findById(Long id)
    {
        Task Task = this.TaskRepository.findById(id).orElse(null);
        
        return Task;
    }

    //Le pasamos el id desde el controller
    public Task updateTask(Long id, Task Task)
    {
        Task TaskFinded = this.findById(id);

        if(TaskFinded == null) return null;
   
        return this.TaskRepository.save(Task);
    }   

    public void delete(Long id)
    {
        this.TaskRepository.deleteById(id);
    }
}
