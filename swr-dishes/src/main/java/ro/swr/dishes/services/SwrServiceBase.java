package ro.swr.dishes.services;

import java.util.List;

public interface SwrServiceBase<M, E> {

    List<M> findAll();

    M findById(Long id);

    void deleteById(Long id);

    M save(M model);


}
