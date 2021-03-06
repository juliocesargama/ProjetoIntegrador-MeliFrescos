package br.com.meli.PIFrescos.repository;

import br.com.meli.PIFrescos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Juliano Alcione de Souza
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String username);

  Optional<User> findById(Integer idUsuario);
}
