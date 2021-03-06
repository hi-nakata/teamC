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
					"B.BOOK_ID \n" +
					",B.TITLE \n" +
					",TO_CHAR(R.DUE_DATE, 'YYYY/MM/DD') AS DUE_DATE \n" +
					",TO_NUMBER(R.DUE_DATE - SYSDATE) AS REST_DATE \n" +
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
					"update \n" +
					"RENTAL \n" +
					"set \n" +
					"RENTAL_STATUS = 0, BACK_DATE = TRUNC(SYSDATE) \n" +
					"where \n" +
					"BOOK_ID = ? \n";

	private static final String SELECT_ALL_ALERT =
					"select \n" +
					"B.BOOK_ID \n" +
					",TO_CHAR(R.DUE_DATE, 'YYYY/MM/DD') AS DUE_DATE \n" +
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
					"and R.RENTAL_STATUS(+)=1 \n" +
					"and TRUNC(SYSDATE) >= R.DUE_DATE ";

	private static final String UPDATE_ALERT =
					"update  \n" +
					"RENTAL \n" +
					"set \n" +
					"ALERT_STATUS = 1 \n" +
					"where \n" +
					"BOOK_ID = ? ";

	private static final String INSERT_RENTAL_CARD =
					"insert into RENTAL \n" +
					"(BOOK_ID,RENTAL_DATE,DUE_DATE,CHECK_DATE,ALERT_STATUS,USER_ID,RENTAL_STATUS,BACK_DATE) \n" +
					"values(?, \n" +
					"TO_DATE(sysdate, 'YYYY//MM/DD'), \n" +
					"TO_DATE(sysdate, 'YYYY//MM/DD'), \n" +
					"TO_DATE('20190101', 'YYYY//MM/DD'), \n" +
					"0, \n" +
					"?, \n" +
					"1, \n" +
					"TO_DATE('29290101', 'YYYY//MM/DD'))";

	static final String SELECT_ALL_HISTORY =
					"select \n" +
					"R.BOOK_ID \n" +
					",B.TITLE \n" +
					",R.COME \n" +
					"from \n" +
					"BOOK B \n" +
					",RENTAL R \n" +
					"where 1=1 \n" +
					"and B.BOOK_ID = R.BOOK_ID(+) \n" +
					"and R.RENTAL_STATUS = 0 \n" +
					"and R.USER_ID = ? \n" +
					"order by BOOK_ID ";

	private static final String SELECT_BY_ID_QUERY =
					"select \n" +
					"R.BOOK_ID \n" +
					",B.TITLE \n" +
					",R.COME \n" +
					"from \n" +
					"BOOK B \n" +
					",RENTAL R \n" +
					"where 1=1 \n" +
					"and B.BOOK_ID = R.BOOK_ID(+) \n" +
					"and R.RENTAL_STATUS = 0 \n" +
					"and R.USER_ID = ? \n" +
					"and R.BOOK_ID = ? \n" +
					"order by BOOK_ID " ;

	private static final String UPDATE_QUERY =
					"update RENTAL \n" +
					"set COME = ? \n" +
					"where 1=1 \n" +
					"and RENTAL_STATUS = 0 \n" +
					"and USER_ID = ? \n" +
					"and BOOK_ID = ? " ;

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
				result.add(owingRentalCardResultSet(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	public List<RentalCard> allHistory(String userId){
		List<RentalCard> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null){
			return result;
		}

		try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HISTORY)){
			statement.setString(1, userId);

			ResultSet rs = statement.executeQuery();

			while (rs.next()){
				result.add(historyRentalCardResultSet(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	public RentalCard findById(String userId, int bookId){

		RentalCard result = null;
		Connection connection =ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}
		try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)){
			statement.setString(1,userId);
			statement.setInt(2,bookId);

			ResultSet rs =statement.executeQuery();

			if(rs.next()){
				result = historyIdRentalCardResultSet(rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	public boolean update(RentalCard rental) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			statement.setString(1, rental.getComment());
			statement.setString(2, rental.getUserId());
			statement.setInt(3, rental.getBookId());
			count = statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return count == 1;
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
				result.add(lateRentalCardResultSet(rs));
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
	public boolean returnBook(int bookId) {
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
	public boolean alert(int bookId) {
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

	public RentalCard rental(int bookId,String userId) {
		Connection connection = ConnectionProvider.getConnection();
		RentalCard rental = new RentalCard();
		rental.setBookId(bookId);
		if (connection == null) {
			return rental;
		}
		try (PreparedStatement statement = connection.prepareStatement(INSERT_RENTAL_CARD)) {
			// INSERT実行
			statement.setInt(1, bookId);
			statement.setString(2, userId);
			statement.executeUpdate();
			// INSERTできたらKEYを取得
			//ResultSet rs = statement.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return rental;
	}

	private RentalCard owingRentalCardResultSet(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setBookId(rs.getInt("BOOK_ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setDueDate(rs.getString("DUE_DATE"));
		result.setRestDate(rs.getInt("REST_DATE"));
		return result;

	}

	private RentalCard lateRentalCardResultSet(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setBookId(rs.getInt("BOOK_ID"));
		result.setDueDate(rs.getString("DUE_DATE"));
		result.setTitle(rs.getString("TITLE"));
		result.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
		result.setAlertStatus(rs.getInt("ALERT_STATUS"));
		return result;

	}

	private RentalCard historyRentalCardResultSet(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setBookId(rs.getInt("BOOK_ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setComment(rs.getString("COME"));
		return result;

	}

	private RentalCard historyIdRentalCardResultSet(ResultSet rs) throws SQLException{
		RentalCard result = new RentalCard();
		result.setBookId(rs.getInt("BOOK_ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setComment(rs.getString("COME"));
		return result;

	}

}
