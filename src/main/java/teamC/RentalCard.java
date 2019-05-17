package teamC;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class RentalCard {
	private int bookId;
	private String title;
	private  String rentalDate;
	private String dueDate;
	private String checkDate;
	private int alertStatus;
	private String userId;
	private int rentalStatus;
	private String backDate;
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRentalDate() {
		return rentalDate;
	}
	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public int getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(int alertStatus) {
		this.alertStatus = alertStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRentalStatus() {
		return rentalStatus;
	}
	public void setRentalStatus(int rentalStatus) {
		this.rentalStatus = rentalStatus;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}



}