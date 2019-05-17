package teamC;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("rentals")
public class RentalResource {

/**本一覧表示の実装**/
	private final RentalDAO dao = new RentalDAO();

	/**一覧用に本の情報を全件取得する**/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> allRentals(){
		System.out.println("hoge");
		return dao.allRentals();
	}
}
