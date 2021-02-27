package com.iddera.repository;

import com.iddera.enums.Status;
import com.iddera.entity.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface ActiveJpaRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus = 'ACTIVE'")
    List<T> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus = 'ACTIVE'")
    Page<T> findAll(Pageable pageable);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus = 'ACTIVE'")
    List<T> findAll(Sort sort);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus = 'ACTIVE'")
    <S extends T> List<S> findAll(Example<S> example);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id in ?1 and e.recordStatus = 'ACTIVE'")
    List<T> findAllById(Iterable<Long> ids);

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus <> 'ACTIVE'")
    List<T> findInActive();

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.recordStatus = ?1")
    List<T> findAllByStatus(Status status);

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e")
    List<T> findAllRegardless();

    @Transactional
    @Modifying
    @Query("delete from #{#entityName} e where e.id = ?1")
    void hardDelete(long id);

    @Transactional
    default void unDeleteById(long id) {
        T entity = findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Id %s does not exist", id)));
        entity.setRecordStatus(Status.ACTIVE);

        save(entity);
    }

    @Transactional
    default void unDelete(T entity) {
        unDeleteById(entity.getId());
    }

    //default implementation for delete added to enable support for JPA Auditing
    //which may not be triggered for a query
    @Override
    @Transactional
    default void delete(T entity) {
        entity.setRecordStatus(Status.INACTIVE);
        save(entity);
    }

    @Override
    @Transactional
    default void deleteById(Long id) {
        T entity = findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Id %s does not exist", id)));
        delete(entity);
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        for(T entity : entities)
            delete(entity);
    }

    @Override
    @Transactional
    default void deleteAll() {
        for(T entity : findAll())
            delete(entity);
    }
}
