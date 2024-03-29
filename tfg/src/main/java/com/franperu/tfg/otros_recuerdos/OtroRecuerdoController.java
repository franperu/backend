package com.franperu.tfg.otros_recuerdos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franperu.tfg.DTO.Mensaje;
import com.franperu.tfg.login.Usuario;
import com.franperu.tfg.login.UsuarioService;

@RestController
@RequestMapping("/api/otros")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.0.21:4200"})
public class OtroRecuerdoController {
	
	@Autowired
	OtroRecuerdoService otrosRecuerdosService;
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/lista")
    public ResponseEntity<List<OtroRecuerdo>> getLista(){
        List<OtroRecuerdo> lista = otrosRecuerdosService.obtenerRecuerdos();
        return new ResponseEntity<List<OtroRecuerdo>>(lista, HttpStatus.OK);
    }
	
	@GetMapping("/lista/{id}")
    public ResponseEntity<List<OtroRecuerdo>> getListaRecuerdos(@PathVariable Long id){
		Usuario usuario = new Usuario();
		usuario.setId(id);
		List<OtroRecuerdo> lista = otrosRecuerdosService.obtenerRecuerdosUsuario(usuario);
        return new ResponseEntity<List<OtroRecuerdo>>(lista, HttpStatus.OK);
    }
	
	@GetMapping("/detalle/{id}")
	public ResponseEntity<OtroRecuerdo> getOne(@PathVariable Long id){
	    if(!otrosRecuerdosService.existePorId(id))
	       return new ResponseEntity(new Mensaje("no existe ese recuerdo"), HttpStatus.NOT_FOUND);
	        
	    OtroRecuerdo recuerdo = otrosRecuerdosService.obtenerPorId(id).get();
	    return new ResponseEntity<OtroRecuerdo>(recuerdo, HttpStatus.OK);
	}
	
	@PostMapping("/nuevo/{id}")
	public ResponseEntity<?> create(@RequestBody OtroRecuerdo recuerdo, @PathVariable Long id) {
		Optional<Usuario> usuarioOptional = usuarioService.getById(id);
		Usuario usuario = new Usuario();
		usuario = usuarioOptional.get();
		recuerdo.setUsuario(usuario);
		otrosRecuerdosService.guardar(recuerdo);
		return new ResponseEntity<OtroRecuerdo>(recuerdo, HttpStatus.CREATED);
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@RequestBody OtroRecuerdo recuerdo, @PathVariable("id") Long id) {
		OtroRecuerdo recuerdoUpdate = otrosRecuerdosService.obtenerPorId(id).get();
		recuerdoUpdate.setTipo(recuerdo.getTipo());
		recuerdoUpdate.setDescripcion(recuerdo.getDescripcion());
		otrosRecuerdosService.guardar(recuerdoUpdate);
		return new ResponseEntity(new Mensaje("recuerdo actualizado"), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if(!otrosRecuerdosService.existePorId(id))
			return new ResponseEntity(new Mensaje("no existe ese recuerdo"), HttpStatus.NOT_FOUND);
		otrosRecuerdosService.borrar(id);
		
		return new ResponseEntity(new Mensaje("recuerdo eliminado"), HttpStatus.OK);
	}

}
