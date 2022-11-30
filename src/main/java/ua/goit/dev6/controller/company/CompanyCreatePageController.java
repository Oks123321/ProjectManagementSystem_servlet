package ua.goit.dev6.controller.company;



import ua.goit.dev6.model.dto.CompanyDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/companyCreate")
public class CompanyCreatePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto emptyCompanyDto = new CompanyDto();
        req.setAttribute("company", emptyCompanyDto);
        req.getRequestDispatcher("/WEB-INF/jsp/company/companyCreate.jsp").forward(req, resp);
    }
}
