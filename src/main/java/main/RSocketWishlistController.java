package main;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@Controller
public class RSocketWishlistController {
	private WishListDao wishListDao;
	private WishListProductDao wishListProductDao;
	private Log logger = LogFactory.getLog(RSocketWishlistController.class);
	
	@Autowired
	public RSocketWishlistController(WishListDao wishListDao, WishListProductDao wishListProductDao) {
		super();
		this.wishListDao = wishListDao;
		this.wishListProductDao = wishListProductDao;
	}
	
	@PostConstruct
	public void init() {
		Hooks.onErrorDropped(e->{
//			if (e instanceof CancellationException 
//				|| e.getCause() instanceof CancellationException) {
//				this.logger.trace("Operator called default onErrorDropped", e);
//			}else {
//				this.logger.error("Error while interacting with consumer", e);
//			}
		});
	}

	// CLI INVOCATION SAMPLE (WINDOWS USE CMD!):
	// java -jar rsc-0.9.1.jar --debug  --request  --data "{\"wishListId\":\"123\", \"name\":\"birthday wishlist\", \"userEmail\":\"dummy@s.afeka.ac.il\",  \"createdTimestamp\":\"2021-12-09T13:38:23.104+0000\"}" --route wishList-create-req-resp tcp://localhost:7000
	@MessageMapping("wishList-create-req-resp")// you can set the controller's invocation string to be anything
	public Mono<WishListBoundary> createWishlist(WishListBoundary input){
		this.logger.debug("Received wishList-create-req-resp: " + input);
		
		// store boundary in DB and return the boundary including updated timestamp and id
		return Mono.just(input) // Mono<WishListBoundary>
			.map(boundary->{
				boundary.setWishListId(null);
				boundary.setCreatedTimestamp(new Date());
				return boundary;
			})// Mono<WishListBoundary>
			.map(this::toEntity) // Mono<WishListEntity>
			.flatMap(this.wishListDao::save) // Mono<WishListEntity>
			.map(this::toBoundary)
			.log();	
	}
	
	// java -jar rsc-0.9.1.jar --debug  --fnf  --data "{\"wishListId\":\"61b77330b17283089c8ddf29\", \"productId\":\"p42\"}" --route addProd-fire-and-forget tcp://localhost:7000
	@MessageMapping("addProd-fire-and-forget")// you can set the controller's invocation string to be anything
	public Mono<Void> addProductToWishlist(WishListProductBoundary input){
		this.logger.debug("Received addProd-fire-and-forget: " + input);
		
		return this.wishListDao
				.existsById(input.getWishListId())
				.flatMap(exist -> {
					if (exist) {
						if (input.getProductId() != null && !input.getProductId().isEmpty()) {
							return this.wishListProductDao.save(toEntity(input));
						}
					}
					return Mono.just(exist);
				})
				.log()
				.then();
	}
	
	// java -jar rsc-0.9.1.jar --debug --stream --data "{\"sortBy\":[\"userEmail\", \"wishListId\"], \"order\":\"ASC\"}" --route getLists-stream tcp://localhost:7000
	@MessageMapping("getLists-stream")
	public Flux<WishListBoundary> getLists(SortBoundary sortBoundary){
		this.logger.debug("Received getWishlists stream: " + sortBoundary);
		
		return this.wishListDao
				.findAll(Sort.by(Sort.Direction.fromString(sortBoundary.getOrder()), sortBoundary.getSortBy()))
				.map(this::toBoundary)
				.log();
	}
	
	// java -jar rsc-0.9.1.jar --debug --stream --data "{\"wishListId\":\"61b77330b17283089c8ddf29\"}" --route getProductsByList-stream tcp://localhost:7000
	@MessageMapping("getProductsByList-stream")
	public Flux<WishListProductBoundary> getProductsByList(WishListBoundary wishlistBoundary){
		this.logger.debug("Received getSpecificWishList stream: " + wishlistBoundary);
		
		return this.wishListProductDao
				.findByWishListId(wishlistBoundary.getWishListId())
				.map(this::toBoundary)
				.log();
	}
	
	
	
	
	
	
	
	private WishListProductEntity toEntity(WishListProductBoundary boundary) {
		WishListProductEntity rv = new WishListProductEntity();
		rv.setWishListId(boundary.getWishListId());
		rv.setProductId(boundary.getProductId());
		return rv;
	}
	
	private WishListProductBoundary toBoundary(WishListProductEntity entity) {
		WishListProductBoundary rv = new WishListProductBoundary();
		rv.setWishListId(entity.getWishListId());
		rv.setProductId(entity.getProductId());
		return rv;
	}
	
	private WishListEntity toEntity(WishListBoundary boundary) {
		WishListEntity rv = new WishListEntity();
		rv.setWishListId(boundary.getWishListId());
		rv.setName(boundary.getName());
		rv.setCreatedTimestamp(boundary.getCreatedTimestamp());
		rv.setUserEmail(boundary.getUserEmail());
		return rv;
	}
	
	private WishListBoundary toBoundary(WishListEntity entity) {
		WishListBoundary rv = new WishListBoundary();
		rv.setWishListId(entity.getWishListId());
		rv.setName(entity.getName());
		rv.setCreatedTimestamp(entity.getCreatedTimestamp());
		rv.setUserEmail(entity.getUserEmail());
		return rv;
	}
	
	
	
	
	
	
	
	
}
