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

	private static final String SELECT_ALL_QUERY =" \n" +"select \n" +"* \n" +"from \n" +"CATEGORY \n" +"order by CAT_ID";
	private static final String SELECT_BY_ID_QUERY=" \n" +"select \n" +"* \n" +"from \n" +"CATEGORY \n" +"where \n" +"CAT_ID=?";
	private static final String SELECT_BY_BOOKID_QUERY="select * \n" +
			"from BOOK_KATEGORY_MASTER BC, CATEGORY CA \n" +
			"where 1=1 \n" +
			"and BC.CAT_ID = CA.CAT_ID \n" +
			"and BC.BOOK_ID = ? \n";
	private static final String INSERT_QUERY="INSERT INTO CATEGORY(CAT_NAME) \n"+"values(?)";
	private static final String UPDATE_QUERY="UPDATE CATEGORY \n" +"SET CAT_NAME = ? \n" +"WHERE CAT_ID = ?";
	private static final String DELETE_QUERY="DELETE FROM CATEGORY WHERE CAT_ID = ?";
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

	/**id指定の検索実施**/
	public Category findByBookId(int id){

		Category result = null;
		Connection connection =ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}
		try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_BOOKID_QUERY)){
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

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "CAT_ID" });) {
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
