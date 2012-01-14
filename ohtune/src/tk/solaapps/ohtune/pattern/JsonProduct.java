package tk.solaapps.ohtune.pattern;

import tk.solaapps.ohtune.model.Product;

public class JsonProduct {
	private String name;
	private String name_eng;
	private String status;
	private String image;
	private String drawing;
	private String our_name;
	private String mold_rate;
	private String machining_pos;
	private String handwork_pos;
	private String polishing;
	private Integer finished;
	private Integer semi_finished;
	private String mold_code;
	private String mold_name;
	private String mold_stand_no;
	
	public JsonProduct(Product product)
	{
		this.name = product.getName();
		this.name_eng = product.getName_eng();
		this.drawing = product.getDrawing();
		this.finished = product.getFinished();
		this.handwork_pos = product.getHandwork_pos();
		this.image = product.getImage();
		this.machining_pos = product.getMachining_pos();
		this.mold_rate = product.getMold_rate();
		this.our_name = product.getOur_name();
		this.polishing = product.getPolishing();
		this.semi_finished = product.getSemi_finished();
		this.status = product.getStatus();
		this.mold_code = product.getMold() == null ? "" : product.getMold().getCode();
		this.mold_name = product.getMold() == null ? "" : product.getMold().getName();
		this.mold_stand_no = product.getMold() == null ? "" : product.getMold().getStand_no();
	}
}
