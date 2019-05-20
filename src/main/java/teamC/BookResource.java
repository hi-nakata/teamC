package teamC;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;


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
	public List<Book> findByParam(@QueryParam("titleParam") String titleParam,
			@QueryParam("bookIdParam") int idParam){
		Param param = new Param(titleParam,idParam);
		return dao.findByParam(param);

	}

	/**
	 * 指定した本を登録する。
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Book create(final FormDataMultiPart form) throws WebApplicationException {
		Book book = new Book();

		book.setId(0);
		book.setTitle(form.getField("title").getValue());
		book.setAuthor(form.getField("author").getValue());
		book.setPublisher(form.getField("publisher").getValue());
		book.setPubdate(form.getField("pubdate").getValue());
		book.setShelf(form.getField("bookshelf").getValue());

		if (!book.isValidObject()) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		return dao.create(book);
	}

}
