package com.teleios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.teleios.model.Categoria;

//public interface ICategoriaRepository extends CrudRepository<Categoria, Integer> {
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

}
