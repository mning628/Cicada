package com.mn.b.repository;

import com.mn.b.entity.BusinessStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessStreamRepository extends JpaRepository<BusinessStream, Integer>
{
    BusinessStream findByBusinessId(String businessId);

}
