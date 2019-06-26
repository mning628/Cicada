package com.mn.a.repository;

import com.mn.a.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>
{
    Account findAccountByIdentity(String identity);
}
