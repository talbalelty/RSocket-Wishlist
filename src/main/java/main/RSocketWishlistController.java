package main;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class RSocketWishlistController {
	private Log logger = LogFactory.getLog(RSocketWishlistController.class);
	
	
	// CLI INVOCATION SAMPLE:
	// java -jar rsc-0.9.1.jar --debug  --request  --data "{\"message\":\"Hello World\", \"author\":\"Demo User\"}" --route request-response tcp://localhost:7000
	@MessageMapping("wishList-create-req-resp")// you can set the controller's invocation string to be anything
	public Mono<WishListBoundary> createWishlist(WishListBoundary input){
		this.logger.debug("Received wishList-create-req-resp: " + input);
		
		// store boundary in DB and return the boundary including updated timestamp and id
		return 
		  Mono.just(input) // Mono<WishListBoundary>
			.map(boundary->{
				boundary.setWishListId(null);
				boundary.setCreatedTimestamp(new Date());
				return boundary;
			})// Mono<WishListBoundary>
			.map(this::toEntity) // Mono<MessageEntity>
			.flatMap(this.messageDao::save) // Mono<MessageEntity>
			.map(this::toBoundary)
			.log();		
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
