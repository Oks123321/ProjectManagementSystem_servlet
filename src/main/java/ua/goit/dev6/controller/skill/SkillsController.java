package ua.goit.dev6.controller.skill;

import com.google.gson.Gson;
import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.config.PropertiesConfig;
import ua.goit.dev6.model.SkillLevel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet("/skills")
public class SkillsController extends HttpServlet {

    private SkillService skillService;

    @Override
    public void init() {
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
        if (req.getParameterMap().containsKey("id")) {
            List<SkillDto> skills = new ArrayList<>();
            skills.add(skillService.findById(Long.valueOf(req.getParameter("id"))).orElseGet(SkillDto::new));
            req.setAttribute("skills", skills);
            req.getRequestDispatcher("/WEB-INF/jsp/skill/skills.jsp").forward(req, resp);
        }

        List<SkillDto> skills = skillService.findAll();
        req.setAttribute("skills", skills);
        req.getRequestDispatcher("/WEB-INF/jsp/skill/skills.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            Optional<SkillDto> skillDto = skillService.findById(Long.valueOf(req.getParameter("id")));
            skillDto.ifPresent((skill) -> skillService.delete(skill));
            req.removeAttribute("id");
            String redirect =
                    resp.encodeRedirectURL(req.getContextPath() + "/skills");
            resp.sendRedirect(redirect);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto skillDto = new SkillDto();
        skillDto.setLanguage(req.getParameter("language"));
        skillDto.setLevel(SkillLevel.valueOf(req.getParameter("level")));
        skillService.create(skillDto);
        String redirect =
                resp.encodeRedirectURL(req.getContextPath() + "/skills");
        resp.sendRedirect(redirect);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        SkillDto skillDto = new Gson().fromJson(requestData, SkillDto.class);
        skillService.update(skillDto);
    }
}
