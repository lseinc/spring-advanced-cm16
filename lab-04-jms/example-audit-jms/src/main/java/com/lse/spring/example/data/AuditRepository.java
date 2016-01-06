package com.lse.spring.example.data;

import com.lse.spring.example.data.Audit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Audit repositor for storing audit entities
 */

@Transactional(timeout=120)
@Repository
public interface AuditRepository extends JpaRepository<Audit,String> {

}