package teamC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

	/**クエリ文字列**/

	private static final String SELECT_ALL_BOOK =
			"select \n" +
					"* \n" +
					" \n" +
					"from \n" +
					"BOOK BO \n" +
					" \n" +
					"LEFT OUTER JOIN RENTAL RE \n" +
					"ON BO.BOOK_ID = RE.BOOK_ID \n" +
					" \n" +
					"LEFT OUTER JOIN ACCOUNT AC \n" +
					"ON RE.USER_ID = AC.USER_ID \n" ;
	private static final String INSERT_QUERY = "INSERT INTO \n" +
			"BOOK(TITLE, AUTHOR, PUBLISHER, YEAR, SHELF) \n" +
			"VALUES(?,?,?,?,?); \n";



	/**本のデータすべてを取得する**/

	public List<Book> allBooks(){
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null){
			return result;
		}

		try(Statement statement = connection.createStatement();){
			ResultSet rs = statement.executeQuery(SELECT_ALL_BOOK);

			while (rs.next()){
				result.add(processRow(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}


	/**パラメータ指定の検索をする**/
	public List<Book> findByParam(Param param){
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null){
			return result;
		}

		String queryString = SELECT_ALL_BOOK +param.getWhereClause();
		try(PreparedStatement statement = connection.prepareStatement(queryString)){
			param.setParameter(statement);

			ResultSet rs = statement.executeQuery();

			while (rs.next()){
				result.add(processRow(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}




/**本のSQLデータ取得**/
	private Book processRow(ResultSet rs) throws SQLException{
		Book result = new Book();
		result.setTitle(rs.getString("title"));
		result.setAuthor(rs.getString("author"));
		result.setPublisher(rs.getString("PUBLISHER"));
		result.setShelf(rs.getString("shelf"));
		result.setRentalStatus(rs.getInt("rental_Status"));
		result.setName(rs.getString("EMPLOYEE_NAME"));
		result.setDueDate(rs.getString("due_Date"));

		return result;

	}


	/**
	 * 指定されたEmployeeオブジェクトを新規にDBに登録する。
	 * 登録されたオブジェクトにはDB上のIDが上書きされる。
	 * 何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param Employee 登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public Book create(Book employee) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return employee;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
//			setParameter(statement, employee, false);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			employee.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return employee;
	}

	/**
	 * オブジェクトからSQLにパラメータを展開する。
	 *
	 * @param statement パラメータ展開対象のSQL
	 * @param employee パラメータに対して実際の値を供給するオブジェクト
	 * @param forUpdate 更新に使われるならtrueを、新規追加に使われるならfalseを指定する。
	 * @throws SQLException パラメータ展開時に何らかの問題が発生した場合に送出される。
	 */
//	private void setParameter(PreparedStatement statement, Book employee, boolean forUpdate) throws SQLException {
//		int count = 1;
//
//		statement.setString(count++, employee.getEmpId());
//		statement.setString(count++, employee.getName());
//		statement.setInt(count++, employee.getAge());
//		statement.setInt(count++, employee.getGender().ordinal());
//		statement.setInt(count++, employee.getPhotoId());
//		statement.setString(count++, employee.getZip());
//		statement.setString(count++, employee.getPref());
//		statement.setString(count++, employee.getAddress());
//		statement.setInt(count++, employee.getPost().getId());
//		if (employee.getEnterDate() != null) {
//			statement.setDate(count++, Date.valueOf(employee.getEnterDate()));
//		} else {
//			statement.setDate(count++, null);
//		}
//		if (employee.getRetireDate() != null) {
//			statement.setDate(count++, Date.valueOf(employee.getRetireDate()));
//		} else {
//			statement.setDate(count++, null);
//		}
//
//		if (forUpdate) {
//			statement.setInt(count++, employee.getId());
//		}
//	}

}
