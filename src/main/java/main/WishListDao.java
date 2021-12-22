package main;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Flux;

public interface WishListDao extends ReactiveMongoRepository<WishListEntity, String> {
  public Flux<WishListEntity> findByUserEmail(@Param("userEmail") String userEmail);
}
