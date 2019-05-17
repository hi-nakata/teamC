package teamC;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 経費関連のサービス実装。
 */
@Path("rentals")
public class RentalResource {
	private final RentalDAO dao = new RentalDAO();

	/**
	 * 一覧用に経費情報を全件取得する。
	 * @return 経費情報のリストをJSON形式で返す。
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RentalCard> findAll() {
		return dao.findAll();
	}

	/**
	 * ID指定で経費情報を取得する。
	 *
	 * @param id 取得対象の経費のID
	 * @return 取得した経費情報をJSON形式で返す。
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RentalCard findById(@PathParam("id") int id) {
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
	/** @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Expense create(Expense expense) throws WebApplicationException {
		validate(expense);
		return dao.create(expense);
	} */

	/**
	 * 指定した情報でDBを更新する。
	 *
	 * @param expense 更新情報を含めた部署情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	/** @PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void update(Expense expense) throws WebApplicationException {
		validate(expense);
		dao.update(expense);
	} */

	/**
	 * 指定したIDの部署情報を削除する。
	 *
	 * @param id 削除対象の部署情報のID
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
	/** private void validate(RentalCard rentalCard) throws WebApplicationException {
		if (rentalCard.getTitle().isEmpty()) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	} */
}
