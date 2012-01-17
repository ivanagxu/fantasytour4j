package tk.solaapps.ohtune.pattern;

import java.util.Date;

import tk.solaapps.ohtune.model.ProductLog;

public class JsonProductLog {
	private Date date;
	private String product_name;
	private String product_our_name;
	private Integer total = 0;
	private Integer finished = 0;
	private Integer rejected = 0;
	private Integer disuse = 0;
	
	public JsonProductLog(ProductLog log)
	{
		this.date = log.getDate();
		this.product_name = log.getProductName();
		this.product_our_name = log.getProductOurName();
		this.total = log.getTotal();
		this.finished = log.getFinished();
		this.rejected = log.getRejected();
		this.disuse = log.getDisuse();
	}
	
}
