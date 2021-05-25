package com.teleios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleios.model.Vacante;

public interface IVacantesRepositiry extends JpaRepository<Vacante, Integer> {

}
