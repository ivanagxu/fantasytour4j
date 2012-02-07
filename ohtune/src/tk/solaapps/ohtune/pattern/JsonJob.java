package tk.solaapps.ohtune.pattern;

import java.util.Date;

import tk.solaapps.ohtune.model.Job;

public class JsonJob {
	private Long order_id;
	private Long id;
	private String number;
	private String order_user;
	private String customer_name;
	private String customer_code;
	private String product_name;
	private String product_our_name;
	
	private String requirement1;
	private String requirement2;
	
	private Integer total;
	private Integer remaining;
	private Date order_deadline;
	private Date order_c_deadline;
	private Date start_date;
	private Date complete_date;
	private String section;
	private String order_status;
	private String order_remark;
	
	private boolean isNew;
	
	private Integer finished;
	private Integer finished_remark;
	private String status;
	private String handled_by;
	private String product_image;
	private String product_drawing;
	
	private Integer total_rejected;
	private String assigned_to;
	
	private Integer priority;
	
	public JsonJob(Job job)
	{
		id = job.getId();
		order_id = job.getOrders().getId();
		
		number = job.getOrders().getNumber();
		
		if(job.getUserac() == null)
			order_user = "";
		else
			order_user = job.getOrders().getCreator();
		
		customer_name = job.getOrders().getCustomer_name();
		customer_code = job.getOrders().getCustomer_code();
		product_name = job.getOrders().getProduct_name();
		product_our_name = job.getOrders().getProduct_our_name();
		requirement1 = job.getOrders().getRequirement_1();
		requirement2 = job.getOrders().getRequirement_2();
		total = job.getTotal();
		order_deadline = job.getOrders().getDeadline();
		order_c_deadline = job.getOrders().getC_deadline();
		start_date = job.getStart_date();
		complete_date = job.getComplete_date();
		section = job.getJob_type().getName();
		order_status = job.getOrders().getStatus();
		order_remark = job.getOrders().getRequirement_4();
		
		finished = job.getFinished();
		status = job.getStatus();
		handled_by = job.getUserac().getName();
		Date now = new Date();
		isNew = job.getStart_date().getYear() == now.getYear() && 
				job.getStart_date().getMonth() == now.getMonth() &&
				job.getStart_date().getDate() == now.getDate();
		product_image = job.getOrders().getProduct_name();
		product_drawing = job.getOrders().getProduct_name();
		remaining = job.getRemaining();
		
		total_rejected = job.getTotal_rejected();
		assigned_to = job.getAssigned_to() == null ? "" : job.getAssigned_to().getName();
		
		priority = job.getOrders().getPriority();
	}
}