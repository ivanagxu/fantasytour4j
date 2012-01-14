package tk.solaapps.ohtune.pattern;

import java.util.Date;

import tk.solaapps.ohtune.model.Order;

public class JsonOrder {
	private Long id;
	private String number;
	private String creator;
	private String product_name;
	private String requirement_1;
	private String requirement_2;
	private String requirement_3;
	private String requirement_4;
	private Date create_date;
	private Date deadline;
	private String status;
	private String product_our_name;
	private Integer quantity;
	private Integer use_finished;
	private Integer use_semi_finished;
	private String customer_name;
	private String customer_code;
	private Float product_rate;
	private Date c_deadline;
	private Integer e_quantity;
	private Integer priority;
	
	
	public JsonOrder(Order order)
	{
		id = order.getId();
		number = order.getNumber();
		creator = order.getCreator();
		product_name = order.getProduct_name();
		requirement_1 = order.getRequirement_1();
		requirement_2 = order.getRequirement_2();
		requirement_3 = order.getRequirement_3();
		requirement_4 = order.getRequirement_4();
		create_date = order.getCreate_date();
		deadline = order.getDeadline();
		status = order.getStatus();
		product_our_name = order.getProduct_our_name();
		quantity = order.getQuantity();
		use_finished = order.getUse_finished();
		use_semi_finished = order.getUse_semi_finished();
		customer_name = order.getCustomer_name();
		customer_code = order.getCustomer_code();
		product_rate = order.getProduct_rate();
		c_deadline = order.getC_deadline();
		e_quantity = order.getE_quantity();
		priority = order.getPriority();
	}
}
