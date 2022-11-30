package ua.goit.dev6.controller.customer;



import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.config.PropertiesConfig;
import ua.goit.dev6.model.dto.CustomerDto;
import ua.goit.dev6.repository.CustomerRepository;
import ua.goit.dev6.service.CustomerService;
import ua.goit.dev6.service.converter.CustomersConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/customerEdit")
public class CustomerUpdatePageController extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        CustomerRepository customerRepository = new CustomerRepository(manager);
        CustomersConverter customersConverter = new CustomersConverter();
        customerService = new CustomerService(customerRepository, customersConverter);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        CustomerDto customerDto = customerService.findById(id).orElseGet(CustomerDto::new);
        req.setAttribute("customer", customerDto);
        req.getRequestDispatcher("/WEB-INF/jsp/customer/customerUpdate.jsp").forward(req, resp);
    }
}
