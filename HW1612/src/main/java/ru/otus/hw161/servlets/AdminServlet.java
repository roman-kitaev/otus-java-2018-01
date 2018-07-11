package ru.otus.hw161.servlets;

import ru.otus.hw161.templateprocessor.TemplateProcessor;

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
public class AdminServlet extends HttpServlet{
    private final TemplateProcessor templateProcessor;

    public AdminServlet() throws IOException{
        templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String userFromSession = (String) httpSession.getAttribute("user");

        if(userFromSession == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String login = userFromSession;

        Map<String, Object> cacheVariables = new HashMap<>();
        cacheVariables.put("user", login);
        int port = request.getServerPort();
        String strPort = "" + port;
        cacheVariables.put("port", strPort);

        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage("wsadmin.html", cacheVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
