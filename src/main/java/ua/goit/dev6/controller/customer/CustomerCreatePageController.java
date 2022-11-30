package ua.goit.dev6.controller.customer;



import ua.goit.dev6.model.dto.CustomerDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerCreate")
public class CustomerCreatePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDto emptyCustomerDto = new CustomerDto();
        req.setAttribute("customer", emptyCustomerDto);
        req.getRequestDispatcher("/WEB-INF/jsp/customer/customerCreate.jsp").forward(req, resp);
    }
}
