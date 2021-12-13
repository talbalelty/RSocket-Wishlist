package main;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WishListDao extends ReactiveMongoRepository<WishListEntity, String> {

}
