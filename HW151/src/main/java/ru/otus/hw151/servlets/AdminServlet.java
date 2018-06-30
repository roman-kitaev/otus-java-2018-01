package ru.otus.hw151.servlets;

import ru.otus.hw151.base.DataSet;
import ru.otus.hw151.cacheengine.SoftRefCacheEngine;
import ru.otus.hw151.templateprocessor.TemplateProcessor;

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
    private final SoftRefCacheEngine<DataSet> cacheEngine;
    private final TemplateProcessor templateProcessor;

    public AdminServlet(SoftRefCacheEngine<DataSet> cacheEngine) throws IOException{
        templateProcessor = TemplateProcessor.instance();
        this.cacheEngine = cacheEngine;
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

        Map<String, Object> cacheVariables = fillWithCacheInfo();
        cacheVariables.put("user", login);

        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage("wsadmin.html", cacheVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> fillWithCacheInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("hit", cacheEngine.getHitCount());
        map.put("miss", cacheEngine.getMissCount());
        map.put("size", cacheEngine.getSize());
        map.put("maxSize", cacheEngine.getMaxElements());
        return map;
    }
}
