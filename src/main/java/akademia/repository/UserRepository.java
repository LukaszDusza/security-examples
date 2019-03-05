package akademia.repository;

import akademia.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp,Integer> {


    @Query(value = "select u from UserApp u where u.login = ?1") //JPQL
    Optional<UserApp> findUserByLogin(String login);
}
