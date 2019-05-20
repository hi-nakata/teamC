package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Category {

	private int categoryId;
	private String categoryName;


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


}
