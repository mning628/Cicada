package com.mn.a.repository;

import com.mn.a.entity.BusinessStream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessStreamRepository extends JpaRepository<BusinessStream, Integer>
{
    BusinessStream findByQueueId(String queueId);

    List<BusinessStream> findByStatus(String status);
}
