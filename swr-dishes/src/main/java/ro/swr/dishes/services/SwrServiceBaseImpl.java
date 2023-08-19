package ro.swr.dishes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.mappers.ModelEntityMapper;

import java.util.List;

@RequiredArgsConstructor
public class SwrServiceBaseImpl<M, E>
        implements SwrServiceBase<M, E> {

    protected final CrudRepository<E, Long> repository;
    protected final ModelEntityMapper<M, E> mapper;

    @Override
    public List<M> findAll() {
        return mapper.fromEntity((List<E>) repository.findAll());
    }

    @Override
    public M findById(Long id) {
        return mapper.fromEntity((E) repository.findById(id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public M save(M model) {
        E saved = repository.save(mapper.toEntity(model));
        return mapper.fromEntity(saved);
    }
}
