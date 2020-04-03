package demo.repository;

import demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUserId(String tenantId);
    User findByEmailAddress(String EmailAddress);
    User findByPhoneNumber(String phoneNumber);
    User findUserByUserType(String userType);
    List<User> findAllUsersByTenant(String tenant);
    List<User> findAllByUserType(String userType);
    User createUser(String userName, String psswd);


}
