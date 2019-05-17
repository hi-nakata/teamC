package teamC;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("books")
public class BookResource {

/**本一覧表示の実装**/
	private final BookDAO dao = new BookDAO();

	/**一覧用に本の情報を全件取得する**/
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Book> allBooks(){
//		System.out.println("hoge");
//		return dao.allBooks();
//	}

	/**本の検索機能の実装**/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> findByParam(@QueryParam("titleParam") String titleParam){
		Param param = new Param(titleParam);
		return dao.findByParam(param);

	}
}
