package com.riwi.daily_tasks.repository;

import com.riwi.daily_tasks.entity.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//Crear un repositorio ------ Recibe la entidad y que tipo es la llave primary
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByTitle(String title);    
}
