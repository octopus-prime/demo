package com.example.produktservice.impl;

import com.example.produktservice.api.Produkt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

//@Api("foo")
@RepositoryRestResource(collectionResourceRel = "produkte", path = "produkte")
@Cacheable("produkte")
public interface ProduktRepository extends MongoRepository<Produkt, UUID> {

//    @Override
//    Optional<Produkt> findById(UUID id);

    //    @RestResource(exported = true, path = "foo")
    Set<Produkt> findAllByIdIn(@RequestParam("produktIds") final Set<UUID> produktIds);
//        return stream(findAllById(produktIds).spliterator(), false).collect(toMap(Produkt::getId, identity()));
//    }


//    @Override
//    @RestResource(exported = true, path = "foo", rel = "foo")
//    Iterable<Produkt> findAllById(@Param("produktIds") Iterable<UUID> produktIds);
//
//    @RestResource(exported = true, path = "blub", rel = "blub")
//    List<Produkt> findAllByIdIn(@Param("produktIds") List<UUID> produktIds);
}
