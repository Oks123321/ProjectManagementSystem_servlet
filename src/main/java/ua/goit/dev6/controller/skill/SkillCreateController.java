package ua.goit.dev6.controller.skill;

import ua.goit.dev6.model.dto.SkillDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skillCreate")
public class SkillCreateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto emptySkillDto = new SkillDto();
        req.setAttribute("skill", emptySkillDto);
        req.getRequestDispatcher("/WEB-INF/jsp/skill/skillCreate.jsp").forward(req, resp);
    }
}
