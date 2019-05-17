package teamC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

	/**クエリ文字列**/

	private static final String SELECT_ALL_BOOK =
			"select \n" +
			"BO.TITLE \n" +
			",BO.AUTHOR \n" +
			",BO.PUBLISHER \n" +
			",BO.SHELF \n" +
			",RE.RENTAL_STATUS \n" +
			",AC.EMPLOYEE_NAME \n" +
			",RE.DUE_DATE \n" +
			"from  \n" +
			"BOOK BO, \n" +
			"RENTAL RE, \n" +
			"ACCOUNT AC \n" +
			"where \n" +
			"1=1 \n" +
			"and RE.RENTAL_STATUS (+) = '1' \n" +
			"and BO.BOOK_ID = RE.BOOK_ID(+) \n" +
			"and RE.USER_ID = AC.USER_ID(+)";


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

}
