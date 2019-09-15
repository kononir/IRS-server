package com.bsuir.inforetrsys.general.repository;

import com.bsuir.inforetrsys.general.repository.specification.SqlSpecification;

import java.util.List;

public interface Repository<T> {
    void save(T element);

    void update(T element);

    void remove(T element);

    List<T> query(SqlSpecification specification);
}
