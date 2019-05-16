package teamC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

	private static final String SQL ="select * \n" +
			"from ACCOUNT \n" +
			"where 1=1 \n" +
			"	and USER_ID = ? \n" +
			"	and PASSWORD = ? \n";


	public boolean auth(Account log) {
		boolean check=false;
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return check;
		}

		try (PreparedStatement statement = connection.prepareStatement(SQL);) {
			setParameter(statement, log, false);
			ResultSet rs = statement.executeQuery();
			check = rs.next();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return check;
	}

	private void setParameter(PreparedStatement statement, Account log, boolean b)throws SQLException{
		int count = 1;

		statement.setString(count++, log.getLoginId());
		statement.setString(count++, log.getLoginPass());
//		statement.setInt(count++, Integer.parseInt(log.getLoginId()));
//		statement.setInt(count++, Integer.parseInt(log.getLoginPass()));

	}
}
