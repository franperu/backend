package com.franperu.tfg.security;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.franperu.tfg.login.Usuario;

import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {

    private Long id;
    private String nombre;
    private String nombreUsuario;
    private String apellidos;
    private Date fechaNacimiento;
    private String domicilio;
    private String identificacion;
    private String telefono;
    private String email;
    private String password;
    private Long familiar;
    private Boolean permiso;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Long id, String nombre, String nombreUsuario, String apellidos, String identificacion, Date fechaNacimiento, String domicilio, String telefono, String email, String password, Long familiar, Boolean permiso, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.familiar = familiar;
        this.permiso = permiso;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getId(), usuario.getNombre(), usuario.getNombreUsuario(), usuario.getApellidos(), usuario.getIdentificacion(), 
        		usuario.getFechaNacimiento(), usuario.getDomicilio(), usuario.getTelefono(), usuario.getEmail(), usuario.getPassword(), usuario.getFamiliar(), usuario.getPermiso(), authorities);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
    
    public Long getFamiliar() {
        return familiar;
    }
    
    public String getApellidos() {
		return apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public Boolean getPermiso() {
		return permiso;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
