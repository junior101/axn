package com.axn.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Repository with global dao methods
 * @param <T> Type of entity
 * @param <ID> Type of primary key
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
    /**
     * Find all record
     * @return List<T>
     */
    List<T> findAll();
    /**
     * Find all record by page
     * @param pageable for example use 'new PageRequest(1, 20)'
     * @return Page<T>
     */
    Page<T> findAll(final Pageable pageable);
}
