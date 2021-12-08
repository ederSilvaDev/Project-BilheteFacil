package br.com.bilhete.facil.Bilhete.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bilhete.facil.Bilhete.model.Usuario;
import br.com.bilhete.facil.Bilhete.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		
		if (usuario == null) {
				throw new UsernameNotFoundException("Usuário não localizado");
		}
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.isEnabled(), true, true, true, usuario.getAuthorities());
	}

	
	
}
