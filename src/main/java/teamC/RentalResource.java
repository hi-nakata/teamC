package teamC;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("rentals")
public class RentalResource {

	private final RentalDAO dao = new RentalDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> allRentals(){
		System.out.println("ok");
		return dao.allRentals();
	}
}
