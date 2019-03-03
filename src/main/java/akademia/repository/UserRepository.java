package akademia.repository;

import akademia.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    @Query(value = "select * from user where name = ?1", nativeQuery = true)
    Optional<Users> findUserByName(String username);
}
