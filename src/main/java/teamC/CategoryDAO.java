package teamC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class CategoryDAO {


	/**クエリ文字列**/

	private static final String SELECT_ALL_QUERY ="select \n" +
			"* \n" +
			"from \n" +
			"CATEGORY";
	private static final String SELECT_BY_ID_QUERY="";
	private static final String INSERT_QUERY="INSERT INTO Category(CAT_ID,CAT_NAME) \n"+"values(?,?)";
	private static final String UPDATE_QUERY="";
	private static final String DELETE_QUERY="";

	/**カテゴリ全件取得**/
	public List<Category> findAll(){
		List<Category> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}

		try(Statement statement = connection.createStatement();){
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while(rs.next()){
				result.add(processRow(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}



	/**id指定の検索実施**/
	public Category findById(int id){

		Category result = null;
		Connection connection =ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}
		try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)){
			statement.setInt(1,id);

			ResultSet rs =statement.executeQuery();

			if(rs.next()){
				result = processRow(rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	/**新規登録**/
	public Category create(Category category){
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return category;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
			statement.setString(1, category.getCategoryName());
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			category.setCategoryId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return category;
	}

	/**DBを更新しテータ更新**/
	public boolean update(Category category){
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			statement.setString(1, category.getCategoryName());
			statement.setInt(2, category.getCategoryId());
			count = statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return count == 1;
	}

	/**idでDBのデータ削除**/

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


	private Category processRow(ResultSet rs) throws SQLException{
		Category result = new Category();
		result.setCategoryId(rs.getInt("CAT_ID"));
		result.setCategoryName(rs.getString("CAT_NAME"));
		return result;
	}



}
