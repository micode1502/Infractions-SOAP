package com.micode.system.infractionsSoap.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.micode.system.infractionsSoap.entity.Infraction;
import com.micode.system.infractionsSoap.repository.InfractionRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class InfractionService {
	@Autowired
	private InfractionRepository repository;
	
	@Transactional(readOnly = true)
	public List<Infraction> findAll(Pageable page){
		try {
			return repository.findAll(page).toList();
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public Infraction create(Infraction infraction) {
		try {
			infraction.setStatus("VIGENTE"); 
			infraction.setDate(new Date()); 
			return repository.save(infraction);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public Infraction update(Infraction infraction) {
		try {
			Infraction object = repository.findById(infraction.getId()).orElseThrow(
	                () -> new IllegalArgumentException("No se encontró ningúna infraccion con el ID: " + infraction.getId())
	        );
			if (infraction.getDni() != null) {
				new IllegalArgumentException("No se encontró ningúna infraccion con el DNI: " + infraction.getDni());
	        }
			if ("ANULADA".equals(object.getStatus())) {
	            object.setStatus("VIGENTE");
	        } else if ("VIGENTE".equals(object.getStatus())) {
	            object.setStatus("ANULADA");
	        } else {
	            throw new IllegalArgumentException("El estado de la infracción no es válido para actualizar.");
	        }
            return repository.save(object);
	    } catch (Exception e) {
	    	throw e;
	    }
	}

	@Transactional
    public void deleteById(Integer id) {
		try {
        	repository.deleteById(id);
		} catch (Exception e) {
			throw e;
		}
    }
	
	@Transactional(readOnly = true)
    public Infraction findByDni(String name) {
        try {
            return repository.findByDni(name);
        } catch (Exception e) {
            throw e;
        }
    }
}
