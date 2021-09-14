package com.assignment.repository;

import com.assignment.entity.AccountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType, Integer> {

    Optional<AccountType> findByType(String accountType);
}
