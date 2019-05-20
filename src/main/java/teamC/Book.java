package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

	/**保持データ**/
	private int id;
	private String title;
	private String author;
	private String publisher;
	private String pubdate;
	private String shelf;
	private int rentalStatus;
	private String name;
	private String dueDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public int getRentalStatus() {
		return rentalStatus;
	}
	public void setRentalStatus(int rentalStatus) {
		this.rentalStatus = rentalStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", publisher=" + publisher + ", shelf=" + shelf
				+ ", rentalStatus=" + rentalStatus + ", name=" + name + ", dueDate=" + dueDate + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public boolean isValidObject() {
		// TODO 判定作る
		return true;
	}

}
