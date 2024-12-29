package finance;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ChatMsgServlet
 */
@WebServlet("/finance")
public class FinanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		
		FinanceDAO dao = new FinanceDAO();
		List<FinanceVO> fList = dao.sumFinanceList();
		
		List<FinanceVO> fList2 = dao.sumStatementList();

		List<FinanceVO> fList3 = dao.sumIncomeList();
		
		request.setAttribute("KEY_SUM_FLIST", fList);
		request.setAttribute("KEY_STATE_FLIST", fList2);
		request.setAttribute("KEY_INCOME_FLIST", fList3);
		
		request.getRequestDispatcher(request.getContextPath() + "/finance_list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
