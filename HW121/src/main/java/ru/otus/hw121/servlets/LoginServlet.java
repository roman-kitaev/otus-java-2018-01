package ru.otus.hw121.servlets;

import ru.otus.hw121.templateprocessor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rel on 02.06.2018.
 */
public class LoginServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;

    public LoginServlet() {
        templateProcessor = TemplateProcessor.instance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (!login.equals("admin") || !pass.equals("admin")) {
            doGet(request, response);
            return;
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", login);

        response.sendRedirect("/admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("user") != null) {
            response.sendRedirect("/admin");
        } else {
            response.setContentType("text/html;charset=utf-8");

            Map<String, Object> variables = new HashMap<>();
            variables.put("msg", "Wrong password or/and login :( Please try again");
            String page = templateProcessor.getPage("index.html", variables);

            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
