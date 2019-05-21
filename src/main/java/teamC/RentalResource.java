package teamC;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("rentals")
public class RentalResource {

	private final RentalDAO dao = new RentalDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> allAlerts(@Context final HttpServletRequest request) {
		System.out.println("期限者一覧");

		HttpSession ses = request.getSession();
		if((boolean) ses.getAttribute("admin")){
			System.out.println("admin");
			return dao.allAlerts();
		}
			return null;
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(@PathParam("id") int id){
		System.out.println("貸出");
		dao.rental(id);
	}

	@PUT
	@Path("rentalStatus/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateRentalStatus(@PathParam("bookId") int bookId){
		System.out.println("返却");
		dao.returnBook(bookId);
	}

	@PUT
	@Path("alertStatus/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateAlertStatus(@PathParam("bookId") int bookId){
		System.out.println("催促");
		dao.alert(bookId);
	}
}



