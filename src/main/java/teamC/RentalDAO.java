package teamC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

	/**クエリ文字列**/

	private static final String SELECT_ALL_RENTAL =
					"select \n" +
					"B.TITLE \n" +
					",R.DUE_DATE \n" +
					",R.RENTAL_STATUS \n" +
					"from \n" +
					"BOOK B \n" +
					",RENTAL R \n" +
					",ACCOUNT A \n" +
					"where 1=1 \n" +
					"and B.BOOK_ID=R.BOOK_ID \n" +
					"and R.USER_ID=A.USER_ID \n" +
					"and A.USER_ID='mirai_kako' \n" +
					"and R.RENTAL_STATUS=1 ";


	/**本のデータすべてを取得する**/

	public List<RentalCard> allRentals(){
		List<RentalCard> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null){
			return result;
		}

		try(Statement statement = connection.createStatement();){
			ResultSet rs = statement.executeQuery(SELECT_ALL_RENTAL);

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

	private RentalCard processRow(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setTitle(rs.getString("TITLE"));
		result.setDueDate(rs.getString("DUE_DATE"));
		result.setRentalStatus(rs.getInt("RENTAL_STATUS"));

		return result;

	}

}
