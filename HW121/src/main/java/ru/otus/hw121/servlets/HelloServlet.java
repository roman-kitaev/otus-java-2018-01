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
 * Created by rel on 12.06.2018.
 */
public class HelloServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;

    public HelloServlet() {
        templateProcessor = TemplateProcessor.instance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("user") != null) {
            response.sendRedirect("/admin");
        } else {
            response.setContentType("text/html;charset=utf-8");

            Map<String, Object> variables = new HashMap<>();
            variables.put("msg", "");
            //variables.put("msg", "Enter your login and password:");
            String page = templateProcessor.getPage("index.html", variables);

            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
