package com.example.moneyapi.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.moneyapi.model.Usuario;
import com.example.moneyapi.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		usuario.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
		return new User(email, usuario.get().getSenha(), getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Optional<Usuario> usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.get().getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao())));
		return authorities;
	}
	
	

}
