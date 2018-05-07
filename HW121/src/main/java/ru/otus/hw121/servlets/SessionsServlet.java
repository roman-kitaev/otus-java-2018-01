package ru.otus.hw121.servlets;

import ru.otus.hw121.base.DataSet;
import ru.otus.hw121.cacheengine.SoftRefCacheEngine;
import ru.otus.hw121.templateprocessor.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SessionsServlet extends HttpServlet {
    private final SoftRefCacheEngine<DataSet> cacheEngine;
    private final TemplateProcessor templateProcessor;

    public SessionsServlet(SoftRefCacheEngine<DataSet> cacheEngine) throws IOException{
        templateProcessor = new TemplateProcessor();
        this.cacheEngine = cacheEngine;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (!login.equals("admin") || !pass.equals("admin")) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Map<String, Object> cacheVariables = new HashMap<>();
        cacheVariables.put("hit", cacheEngine.getHitCount());
        cacheVariables.put("miss", cacheEngine.getMissCount());
        cacheVariables.put("size", cacheEngine.getSize());
        cacheVariables.put("maxSize", cacheEngine.getMaxElements());

        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage("admin.html", cacheVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
