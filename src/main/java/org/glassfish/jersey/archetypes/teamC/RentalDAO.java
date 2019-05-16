package org.glassfish.jersey.archetypes.teamC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 経費データを扱うDAO
 */
public class RentalDAO {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_ALL_QUERY = "SELECT REQ_ID, REQ_DATE, NAME, TITLE, MONEY FROM EXPENSE ORDER BY REQ_ID";
	private static final String SELECT_BY_ID_QUERY = "SELECT REQ_ID, REQ_DATE, NAME, TITLE, MONEY FROM EXPENSE WHERE REQ_ID = ?";
	private static final String INSERT_QUERY = "INSERT INTO EXPENSE(REQ_DATE, NAME, TITLE, MONEY) VALUES (?,?,?,?)";
	private static final String UPDATE_QUERY = "UPDATE EXPENSE SET REQ_DATE = ?,NAME = ?,TITLE = ?,MONEY = ? WHERE REQ_ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM EXPENSE WHERE REQ_ID = ?";

	/**
	 * 経費の全件を取得する。
	 *
	 * @return DBに登録されている経費データ全件を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public List<RentalCard> findAll() {
		List<RentalCard> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

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
	 * ID指定の検索を実施する。
	 *
	 * @param id 検索対象のID
	 * @return 検索できた場合は検索結果データを収めたExpenseインスタンス。検索に失敗した場合はnullが返る。
	 */
	public RentalCard findById(int id) {
		RentalCard result = null;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				result = processRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * 指定されたExpenseオブジェクトを新規にDBに登録する。
	 * 登録されたオブジェクトにはDB上のIDが上書きされる。
	 * 何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param rental 登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public RentalCard create(RentalCard rental) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return rental;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "REQ_ID" });) {
			// INSERT実行
			statement.setString(1, rental.getDate());
			statement.setString(2, rental.getName());
			statement.setString(3, rental.getTitle());
			statement.setInt(4, rental.getMoney());
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			rental.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return rental;
	}

	/**
	 * 指定されたExpenseオブジェクトを使ってDBを更新する。
	 *
	 * @param expense 更新対象オブジェクト
	 * @return 更新に成功したらtrue、失敗したらfalse
	 */
	public boolean update(Expense expense) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			statement.setString(1, expense.getDate());
			statement.setString(2, expense.getName());
			statement.setString(3, expense.getTitle());
			statement.setInt(4, expense.getMoney());
			statement.setInt(5, expense.getId());
			count = statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return count == 1;
	}

	/**
	 * 指定されたIDのExpenseデータを削除する。
	 *
	 * @param id 削除対象のExpenseデータのID
	 * @return 削除が成功したらtrue、失敗したらfalse
	 */
	public boolean remove(int id) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			// DELETE実行
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}

	/**
	 * 検索結果行をオブジェクトとして構成する。
	 * @param rs 検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたExpenseインスタンス
	 * @throws SQLException ResultSetの処理中発生した例外
	 */
	private Expense processRow(ResultSet rs) throws SQLException {
		Expense result = new Expense();
		result.setId(rs.getInt("REQ_ID"));
		result.setDate(rs.getString("REQ_DATE"));
		result.setName(rs.getString("NAME"));
		result.setTitle(rs.getString("TITLE"));
		result.setMoney(rs.getInt("MONEY"));
		return result;
	}
}
