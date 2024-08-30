package iftm.edu.br.dnolavo.mockmvc.services.util;

import org.springframework.stereotype.Component;
import iftm.edu.br.dnolavo.mockmvc.services.exceptions.ResourceNotFoundException;


@Component
public class Validador {
	
	public void eValido(Long id) {
		if (id<0 || id>100000) {
			throw new ResourceNotFoundException("Invalid Id : " + id);
		}
	}

}