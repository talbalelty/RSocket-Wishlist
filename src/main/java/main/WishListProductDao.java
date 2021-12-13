package main;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WishListProductDao extends ReactiveMongoRepository<WishListProductEntity, String> {

}
