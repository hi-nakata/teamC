package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class RentalCard {
	private int bookId;
	private String title;
	private String employeeName;
	private String dueDate;
	private int rentalStatus;
	private int alertStatus;

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
		return "RentalCard [bookId=" + bookId + ", title=" + title + ", employeeName=" + employeeName + ", dueDate=" + dueDate + ", rentalStatus=" + rentalStatus + ", alertStatus=" + alertStatus + "]";
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getAlertStatus() {
		return alertStatus;
	}

	public void setAlertStatus(int alertStatus) {
		this.alertStatus = alertStatus;
	}
}