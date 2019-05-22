package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Category {

	private int categoryId;
	private String categoryName;
	private int bookId;


	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}


}
