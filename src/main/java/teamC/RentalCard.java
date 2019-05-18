package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class RentalCard {
	private int bookId;
	private String title;
	private String dueDate;
	private int rentalStatus;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public int getRentalStatus() {
		return rentalStatus;
	}

	public void setRentalStatus(int rentalStatus) {
		this.rentalStatus = rentalStatus;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "RentalCard [bookId=" + bookId + ", title=" + title + ", dueDate=" + dueDate + ", rentalStatus=" + rentalStatus + "]";
	}
}