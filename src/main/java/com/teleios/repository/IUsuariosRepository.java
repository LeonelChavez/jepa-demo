package com.teleios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleios.model.Usuario;

public interface IUsuariosRepository extends JpaRepository<Usuario, Integer> {

}
