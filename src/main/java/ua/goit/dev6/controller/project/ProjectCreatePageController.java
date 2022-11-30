package ua.goit.dev6.controller.project;


import ua.goit.dev6.model.dto.ProjectDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/projectCreate")
public class ProjectCreatePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDto emptyProjectDto = new ProjectDto();
        req.setAttribute("project", emptyProjectDto);
        req.setAttribute("dateValue", null);
        req.getRequestDispatcher("/WEB-INF/jsp/project/projectCreate.jsp").forward(req, resp);
    }
}
