package org.serratec.petropet.service;

import org.serratec.petropet.entity.Usuario;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalheImp implements UserDetailsService {
    private UsuarioRepository repository;

    // isso tudo aqui é a mesma coisa que @Autowired
    // injeção de dependência através do construtor passando o repositorio
   public UsuarioDetalheImp(UsuarioRepository repository) {
       super();
       this.repository = repository;
   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
       Usuario usuario = repository.findByEmail(username)
               .orElseThrow(()-> new UsernameNotFoundException("Email não encontrado"));
       return usuario;
    }
}
