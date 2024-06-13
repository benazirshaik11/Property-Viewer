package com.skenariolabs.propertyviewer.repository;


import com.skenariolabs.propertyviewer.model.repo.Building;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BuildingRepository extends ReactiveCrudRepository<Building, Long> {
//    @Query("SELECT * FROM building ORDER BY :sortField LIMIT :pageSize OFFSET :offset")
//    Flux<Building> findAllBy(String sortField, int pageSize, int offset);

    Flux<Building> findAllBy(Pageable pageable);
//    Mono<Page<Building>> findAllBy(Pageable pageable);
//Flux<Building> findAllBy();

}
