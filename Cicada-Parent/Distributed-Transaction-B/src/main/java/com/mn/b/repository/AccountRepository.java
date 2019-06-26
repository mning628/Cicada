package com.mn.b.repository;

import com.mn.b.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>
{
    Account findByIdentity(String identity );
}
