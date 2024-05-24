package validator.mau_sac;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({
		"/mau-sac/store"
})
public class StoreRequest implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();

		String ma = request.getParameter("ma");
		String ten = request.getParameter("ma");
		String ttS = request.getParameter("trangThai");

		if (ma == null || ten == null || ttS == null ||
				ma.trim().length() == 0 || ten.trim().length() == 0 || ttS.trim().length() == 0) {
			session.setAttribute("error", "Khong duoc de trong");
			response.sendRedirect("/mau-sac/create");
		} else {
			filterChain.doFilter(servletRequest,servletResponse);
		}
	}
}
