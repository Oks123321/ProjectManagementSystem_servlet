package ua.goit.dev6.controller.skill;


import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.config.PropertiesConfig;
import ua.goit.dev6.model.dto.SkillDto;
import ua.goit.dev6.repository.SkillRepository;
import ua.goit.dev6.service.SkillService;
import ua.goit.dev6.service.converter.SkillConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/skillEdit")
public class SkillUpdateController extends HttpServlet {
    private SkillService skillService;

    @Override
    public void init() throws ServletException {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        SkillRepository skillRepository = new SkillRepository(manager);
        SkillConverter skillConverter = new SkillConverter();
        skillService = new SkillService(skillRepository, skillConverter);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        SkillDto skillDto = skillService.findById(id).orElseGet(SkillDto::new);
        req.setAttribute("skill", skillDto);
        req.getRequestDispatcher("/WEB-INF/jsp/skill/skillUpdate.jsp").forward(req, resp);
    }
}
