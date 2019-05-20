package teamC;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("categories")
public class CategoryResource {
	private final CategoryDAO dao = new CategoryDAO();

	/**
	 * 一覧用にカテゴリ情報を全件取得する。
	 * @return カテゴリ情報のリストをJSON形式で返す。
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> findAll() {
		return dao.findAll();
	}

	/**
	 * ID指定でカテゴリ情報を取得する。
	 *
	 * @param id 取得対象のカテゴリのID
	 * @return 取得したカテゴリ情報をJSON形式で返す。
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category findById(@PathParam("id") int id) {
		return dao.findById(id);
	}

	/**
	 * 指定した部署情報を登録する。
	 * DB上のIDがセットされて返却される。
	 *
	 * @param post 登録対象の部署情報
	 * @return DB上のIDがセットされた部署情報。失敗した場合IDが0のまま。
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category create(Category category) throws WebApplicationException {
		validate(category);
		return dao.create(category);
	}

	/**
	 * 指定した情報でDBを更新する。
	 *
	 * @param post 更新情報を含めた部署情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void update(Category category) throws WebApplicationException {
		validate(category);
		dao.update(category);
	}

	/**
	 * 指定したIDの情報カテゴリを削除する。
	 *
	 * @param id 削除対象のカテゴリ情報のID
	 */
	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}

	/**
	 * 入力内容のチェックを行う。
	 * @param post 入力データを保持したモデル
	 * @throws ValidationException 入力データチェックに失敗した場合に送出される。
	 */
	private void validate(Category category) throws WebApplicationException {
		if (category.getCategoryName().isEmpty()) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

}
