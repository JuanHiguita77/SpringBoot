package com.riwi.MyFirstWeb.repository;

import com.riwi.MyFirstWeb.entity.Coder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//Crear un repositorio
public interface CoderRepository extends JpaRepository<Coder, Long>{}//Recibe la entidad y que tipo es la llave primary
