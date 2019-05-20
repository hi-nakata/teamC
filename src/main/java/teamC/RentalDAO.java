package teamC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

	private static final String SELECT_ALL_RENTAL =
					"select \n" +
					"B.BOOK_ID"+
					",B.TITLE \n" +
					",R.DUE_DATE \n" +
					",R.RENTAL_STATUS \n" +
					"from \n" +
					"BOOK B \n" +
					",RENTAL R \n" +
					",ACCOUNT A \n" +
					"where 1=1 \n" +
					"and B.BOOK_ID=R.BOOK_ID(+) \n" +
					"and R.USER_ID=A.USER_ID(+) \n" +
					"and A.USER_ID= ? \n" +
					"and R.RENTAL_STATUS(+)=1 ";


	private static final String UPDATE_RENTAL =
					"update  \n" +
					"RENTAL \n" +
					"set \n" +
					"RENTAL_STATUS = 0 \n" +
					"where \n" +
					"BOOK_ID = ? ";

	private static final String SELECT_ALL_ALERT =
					"select \n" +
					"B.BOOK_ID \n" +
					",R.DUE_DATE \n" +
					",B.TITLE \n" +
					",A.EMPLOYEE_NAME \n" +
					",R.ALERT_STATUS \n" +
					"from \n" +
					"BOOK B \n" +
					",RENTAL R \n" +
					",ACCOUNT A \n" +
					"where 1=1 \n" +
					"and B.BOOK_ID=R.BOOK_ID(+) \n" +
					"and R.USER_ID=A.USER_ID(+) \n" +
					"and R.RENTAL_STATUS(+)='1' \n" +
					"and TRUNC(SYSDATE) <= R.DUE_DATE ";

	private static final String UPDATE_ALERT =
					"update  \n" +
					"RENTAL \n" +
					"set \n" +
					"ALERT_STATUS = 1 \n" +
					"where \n" +
					"BOOK_ID = ? ";

	public List<RentalCard> allRentals(String userId){
		List<RentalCard> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null){
			return result;
		}

		try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RENTAL)){
			statement.setString(1, userId);

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

	public List<RentalCard> allAlerts() {
		List<RentalCard> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_ALERT);

			while (rs.next()) {
				result.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * 指定されたIDのbookデータのRentalStatusを更新する。
	 *
	 * @param id 更新対象のbookデータのID
	 * @return 更新が成功したらtrue、失敗したらfalse
	 */
	public boolean updateRentalStatus(int bookId) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_RENTAL)) {
			// UPDATE実行
			statement.setInt(1, bookId);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}

	/**
	 * 指定されたIDのbookデータのAlertStatusを更新する。
	 *
	 * @param id 更新対象のbookデータのID
	 * @return 更新が成功したらtrue、失敗したらfalse
	 */
	public boolean updateAlertStatus(int bookId) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_ALERT)) {
			// UPDATE実行
			statement.setInt(1, bookId);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}

	private RentalCard processRow(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setBookId(rs.getInt("BOOK_ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setDueDate(rs.getString("DUE_DATE"));
		result.setRentalStatus(rs.getInt("RENTAL_STATUS"));
		return result;

	}

}
