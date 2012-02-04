package tk.solaapps.ohtune.model;

public class ProductionLog {
	private String product_our_name = "";
	private Integer finished = 0;
	private Integer rejected = 0;
	private Integer disuse = 0;
	
	
	public String getProduct_our_name() {
		return product_our_name;
	}
	public void setProduct_our_name(String product_our_name) {
		this.product_our_name = product_our_name;
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
