package com.micode.system.infractionsSoap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micode.system.infractionsSoap.entity.Infraction;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction, Integer>{
	Infraction findByDni(String dni);
}
