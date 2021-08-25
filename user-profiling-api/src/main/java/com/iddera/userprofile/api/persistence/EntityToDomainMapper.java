package com.iddera.userprofile.api.persistence;

public interface EntityToDomainMapper<T, V> {
    V toEntity(T model);

    V toEntity(T model, Long id);

    T toModel(V entity);
}
