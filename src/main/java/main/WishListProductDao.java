package main;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Flux;

public interface WishListProductDao extends ReactiveMongoRepository<WishListProductEntity, String> {

	public Flux<WishListProductEntity> findByWishListId(@Param("wishListId") String wishListId);

}
