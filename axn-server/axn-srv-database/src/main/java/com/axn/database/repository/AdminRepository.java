package com.axn.database.repository;

import com.axn.database.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * Custom dao methods for Admin repository
 */
@Repository
public interface AdminRepository extends BaseRepository<Admin, Long> {
}
