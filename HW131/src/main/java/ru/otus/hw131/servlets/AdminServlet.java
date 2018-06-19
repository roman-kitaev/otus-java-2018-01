package ru.otus.hw131.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.hw131.base.DataSet;
import ru.otus.hw131.cacheengine.SoftRefCacheEngine;
import ru.otus.hw131.templateprocessor.TemplateProcessor;

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

    @Autowired
    private SoftRefCacheEngine<DataSet> cacheEngine;

    @Autowired
    private TemplateProcessor templateProcessor;

    public AdminServlet() {}

    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        super.init();
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
        String page = templateProcessor.getPage("admin.html", cacheVariables);
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
