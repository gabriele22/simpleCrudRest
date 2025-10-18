package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.model.PersistanceObject;

import java.util.List;
import java.util.Optional;

public interface Dao<PO extends PersistanceObject, ID> {

    PO save(PO po);

    Optional<PO> findById(ID id);

    List<PO> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

}
