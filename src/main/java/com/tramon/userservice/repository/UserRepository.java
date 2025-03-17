package com.tramon.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tramon.userservice.entity.UserEntity;


// -- @Repository — Spring this is a repository
// requires to be registered in the context
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // JpaRepository  auto-generates:
    //-- findAll()
    //-- findById(Long id)
    //-- save(UserEntity user)
    //-- deleteById(Long id)

}

