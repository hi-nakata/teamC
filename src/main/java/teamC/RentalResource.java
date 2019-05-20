package teamC;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("rentals")
public class RentalResource {

	private final RentalDAO dao = new RentalDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> allAlerts() {
		System.out.println("期限者一覧");
		return dao.allAlerts();
	}

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> allRentals(@PathParam("userId") String userId){
		System.out.println("借りている本一覧");
		return dao.allRentals(userId);
	}

	@POST
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void create(@PathParam("id") int id){
		System.out.println("貸出");
		dao.create(id);
	}

	@PUT
	@Path("rentalStatus/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateRentalStatus(@PathParam("bookId") int bookId){
		System.out.println("返却");
		dao.updateRentalStatus(bookId);
	}

	@PUT
	@Path("alertStatus/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateAlertStatus(@PathParam("bookId") int bookId){
		System.out.println("催促");
		dao.updateAlertStatus(bookId);
	}
}



