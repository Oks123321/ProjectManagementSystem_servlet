package ua.goit.dev6.controller.company;

import com.google.gson.Gson;
import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.config.PropertiesConfig;
import ua.goit.dev6.model.dto.CompanyDto;
import ua.goit.dev6.repository.CompanyRepository;
import ua.goit.dev6.service.CompanyService;
import ua.goit.dev6.service.converter.CompanyConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet("/companies")
public class CompaniesController extends HttpServlet {

    private CompanyService companyService;

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");

        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        CompanyRepository companyRepository = new CompanyRepository(manager);
        CompanyConverter companyConverter = new CompanyConverter();
        companyService = new CompanyService(companyRepository, companyConverter);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            List<CompanyDto> companies = new ArrayList<>();
            companies.add(companyService.findById(Long.valueOf(req.getParameter("id"))).orElseGet(CompanyDto::new));
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/WEB-INF/jsp/company/companies.jsp").forward(req, resp);
        }

        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        req.getRequestDispatcher("/WEB-INF/jsp/company/companies.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            Optional<CompanyDto> companyDto = companyService.findById(Long.valueOf(req.getParameter("id")));
            companyDto.ifPresent((company) -> companyService.delete(company));
            req.removeAttribute("id");
            String redirect =
                    resp.encodeRedirectURL(req.getContextPath() + "/companies");
            resp.sendRedirect(redirect);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName(req.getParameter("name"));
        companyDto.setCountry(req.getParameter("country"));
        companyService.create(companyDto);
        String redirect =
                resp.encodeRedirectURL(req.getContextPath() + "/companies");
        resp.sendRedirect(redirect);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        CompanyDto companyDto = new Gson().fromJson(requestData, CompanyDto.class);
        companyService.update(companyDto);
    }
}
