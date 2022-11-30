package ua.goit.dev6.controller.developer;

import com.google.gson.Gson;
import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.config.PropertiesConfig;
import ua.goit.dev6.model.dto.DeveloperDto;
import ua.goit.dev6.repository.DeveloperRepository;
import ua.goit.dev6.repository.DeveloperSkillRelationRepository;
import ua.goit.dev6.repository.SkillRepository;
import ua.goit.dev6.service.DeveloperService;
import ua.goit.dev6.service.converter.DeveloperConverter;

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

@WebServlet("/developers")
public class DevelopersController extends HttpServlet {

    private DeveloperService developerService;

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        DeveloperRepository developerRepository = new DeveloperRepository(manager);
        DeveloperSkillRelationRepository dsRelationRepository = new DeveloperSkillRelationRepository(manager);
        SkillRepository skillRepository = new SkillRepository(manager);
        DeveloperConverter developerConverter = new DeveloperConverter();
        developerService = new DeveloperService(developerRepository, dsRelationRepository,
                skillRepository, developerConverter);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            List<DeveloperDto> developers = new ArrayList<>();
            developers.add(developerService.findById(Long.valueOf(req.getParameter("id"))).orElseGet(DeveloperDto::new));
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/WEB-INF/jsp/developer/developers.jsp").forward(req, resp);
        }
        if (req.getParameterMap().containsKey("projectId")) {
            List<DeveloperDto> developers = developerService.findByProjectId(Long.valueOf(req.getParameter("projectId")));
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/WEB-INF/jsp/developer/developers.jsp").forward(req, resp);
        }

        List<DeveloperDto> developers = developerService.findAll();
        req.setAttribute("developers", developers);
        req.getRequestDispatcher("/WEB-INF/jsp/developer/developers.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            Optional<DeveloperDto> developerDto = developerService.findById(Long.valueOf(req.getParameter("id")));
            developerDto.ifPresent((developer) -> developerService.delete(developer));
            req.removeAttribute("id");
            String redirect =
                    resp.encodeRedirectURL(req.getContextPath() + "/developers");
            resp.sendRedirect(redirect);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setFirst_name(req.getParameter("first_name"));
        developerDto.setLast_name(req.getParameter("last_name"));
        developerDto.setAge(Integer.valueOf(req.getParameter("age")));
        developerDto.setSalary(Integer.valueOf(req.getParameter("salary")));
        developerService.create(developerDto);
        String redirect =
                resp.encodeRedirectURL(req.getContextPath() + "/developers");
        resp.sendRedirect(redirect);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        DeveloperDto developerDto = new Gson().fromJson(requestData, DeveloperDto.class);
        developerService.update(developerDto);
    }
}
