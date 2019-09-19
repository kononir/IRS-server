package com.bsuir.inforetrsys.general.api.repository;

import java.util.List;

public interface Repository<T> {
    void save(T element);

    void update(T element);

    void remove(T element);

    List<T> query(SqlSpecification specification);
}
