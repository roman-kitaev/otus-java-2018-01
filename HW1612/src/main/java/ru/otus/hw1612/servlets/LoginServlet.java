package ru.otus.hw1612.servlets;

import ru.otus.hw1612.templateprocessor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rel on 02.06.2018.
 */
public class LoginServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;
    private static Map<String, String> acountsMap = new HashMap<>();
    static {
        acountsMap.put("admin", "admin");
        acountsMap.put("admin2", "admin2");
    }
    private static Set<String> acountIsLogged = new HashSet<>();

    public LoginServlet() throws IOException{
        templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        boolean logged = false;
        for(Map.Entry<String, String> entry : acountsMap.entrySet()) {
            if(login.equals(entry.getKey()) && pass.equals(entry.getValue())) {
                logged = true;
                break;
            }
        }

        if(!logged || acountIsLogged.contains(login)) {
            doGet(request, response);
            return;
        }

        acountIsLogged.add(login);
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
            variables.put("msg", "Wrong password/login or this account is already logged! Please try again");
            String page = templateProcessor.getPage("index.html", variables);

            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
