package tk.solaapps.ohtune.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.solaapps.ohtune.model.Product;
import tk.solaapps.ohtune.model.ProductRate;
import tk.solaapps.ohtune.model.UserAC;
import tk.solaapps.ohtune.pattern.JsonDataWrapper;
import tk.solaapps.ohtune.pattern.OhtuneLogger;
import tk.solaapps.ohtune.pattern.OhtuneServiceHolder;
import tk.solaapps.ohtune.service.IOhtuneService;

import com.google.gson.Gson;

/**
 * Servlet implementation class ReportController
 */
@WebServlet("/ReportController")
public class ReportController extends HttpServlet implements IOhtuneController{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter("action");
		process(actionName, request, response);
	}

	@Override
	public void process(String actionName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(actionName == null || actionName.equals(""))
		{
			OhtuneLogger.error("action name is null in ReportController");
		}
		else if(actionName.equals("generateProductRateReportByProduct"))
		{
			generateProductRateReportByProduct(request, response);
		}
		else
		{
			OhtuneLogger.error("Unknow action name in ReportController, actionName=" + actionName);
		}
	}

	private void generateProductRateReportByProduct(HttpServletRequest request,	HttpServletResponse response) throws IOException
	{
		UserAC sessionUser = new UserAC();
		if(request.getSession().getAttribute("user") != null)
			sessionUser = (UserAC)request.getSession().getAttribute("user");
		
		String product_name = request.getParameter("product_name");
		
		IOhtuneService service = (IOhtuneService)OhtuneServiceHolder.getInstence().getBeanFactory().getBean("uhtuneService");
		Product product = service.getProductByName(product_name);
		List<ProductRate> rates = service.generateProductRateByProduct(product, sessionUser);
		
		Gson gson = service.getGson();
		
		JsonDataWrapper dw = new JsonDataWrapper(rates, JsonDataWrapper.TYPE_PRODUCT_RATE);
		response.getOutputStream().write(gson.toJson(dw).getBytes("utf-8"));
	}
}
