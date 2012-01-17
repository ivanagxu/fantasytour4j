package tk.solaapps.ohtune.model;

import java.util.Date;

public class ProductLog {
	private JobType jobType;
	private String productName;
	private String productOurName;
	private Date date;
	private Integer total = 0;
	private Integer finished = 0;
	private Integer rejected = 0;
	private Integer disuse = 0;
	public JobType getJobType() {
		return jobType;
	}
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductOurName() {
		return productOurName;
	}
	public void setProductOurName(String productOurName) {
		this.productOurName = productOurName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public Integer getRejected() {
		return rejected;
	}
	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}
	public Integer getDisuse() {
		return disuse;
	}
	public void setDisuse(Integer disuse) {
		this.disuse = disuse;
	}
	
	
}
