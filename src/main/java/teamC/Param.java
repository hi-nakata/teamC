package teamC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAOにクエリーパラムを引き渡すためのオブジェクト。
 */
public class Param {
	private final static String BASE_WHERE_CLAUSE = " WHERE ";

	private String titleParam;
	private int idParam;

	public Param(String titleParam,int idParam){
		this.setTitleParam(titleParam);
		this.setIdParam(idParam);
	}

	public String getTitleParam() {
		return titleParam;
	}

	public void setTitleParam(String titleParam) {
		this.titleParam = titleParam;
	}

	/**登録されているパラメータの状態からWHERE句を生成する**/

	public String getWhereClause(){

		StringBuilder whereClause = new StringBuilder();
		if(titleParam != null){
			if(whereClause.length() == 0){
				whereClause.append(BASE_WHERE_CLAUSE);
			}else{
				whereClause.append(" AND ");
			}
			whereClause.append("BO.TITLE LIKE ?");
		}
		if(idParam != 0){
			if(whereClause.length() == 0){
				whereClause.append(BASE_WHERE_CLAUSE);

			}else{
				whereClause.append(" AND ");
			}
			whereClause.append("BO.BOOK_ID LIKE ?");
		}
		return whereClause.toString();

	}
	public void setParameter(PreparedStatement statement) throws SQLException {
		int count = 1;
		if(titleParam != null){
			statement.setString(count++, titleParam);
		}
		if(idParam != 0){
			statement.setInt(count++, idParam);
		}
	}

	public int getIdParam() {
		return idParam;
	}

	public void setIdParam(int idParam) {
		this.idParam = idParam;
	}

//	private int postId;
//	private String empId;
//	private String nameParam;
//
//	public Param(int postId, String empId, String nameParam) {
//		this.setPostId(postId);
//		this.setEmpId(empId);
//		this.setNameParam(nameParam);
//	}
//
//	public int getPostId() {
//		return postId;
//	}
//	public void setPostId(int postId) {
//		this.postId = postId;
//	}
//
//	public String getEmpId() {
//		return empId;
//	}
//	public void setEmpId(String empId) {
//		this.empId = empId == null ? "" : empId;
//	}
//
//	public String getNameParam() {
//		return nameParam;
//	}
//	public void setNameParam(String nameParam) {
//		this.nameParam = nameParam == null ? "" : "%"+nameParam+"%";
//	}
//
//	/**
//	 * 登録されているパラメータの状態からWHERE句を生成する。
//	 *
//	 * @return SQLのWHERE句
//	 */
//	public String getWhereClause() {
//		StringBuilder whereClause = new StringBuilder();
//		if (postId != 0) {
//			if (whereClause.length() == 0) {
//				whereClause.append(BASE_WHERE_CLAUSE);
//			} else {
//				whereClause.append(" AND ");
//			}
//			whereClause.append("EMP.POSTID = ?");
//		}
//		if (!empId.isEmpty()) {
//			if (whereClause.length() == 0) {
//				whereClause.append(BASE_WHERE_CLAUSE);
//			} else {
//				whereClause.append(" AND ");
//			}
//			whereClause.append("EMP.EMPID = ?");
//		}
//		if (!nameParam.isEmpty()) {
//			if (whereClause.length() == 0) {
//				whereClause.append(BASE_WHERE_CLAUSE);
//			} else {
//				whereClause.append(" AND ");
//			}
//			whereClause.append("EMP.NAME LIKE ?");
//		}
//
//		// ORDER BYは最後に指定する
//		whereClause.append(" ORDER BY EMP.ID");
//
//		return whereClause.toString();
//	}
//
//	/**
//	 * getWhereClauseメソッドで設定されたWHERE句を含むSQLにパラメータをセットする
//	 *
//	 * @param statement パラメータをセットする対象のPreparedStatement
//	 * @throws SQLException パラメータの設定時に何らかの問題があった場合
//	 */
//	public void setParameter(PreparedStatement statement) throws SQLException {
//		int count = 1;
//		if (postId != 0) {
//			statement.setInt(count++, postId);
//		}
//		if (!empId.isEmpty()) {
//			statement.setString(count++, empId);
//		}
//		if (!nameParam.isEmpty()) {
//			statement.setString(count++, nameParam);
//		}
//	}
}
