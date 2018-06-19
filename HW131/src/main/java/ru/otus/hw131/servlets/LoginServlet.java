package ru.otus.hw131.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.hw131.base.MyDBService;
import ru.otus.hw131.base.UserDataSet;
import ru.otus.hw131.templateprocessor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rel on 02.06.2018.
 */
public class LoginServlet extends HttpServlet {

    @Autowired
    private TemplateProcessor templateProcessor;

    @Autowired
    private MyDBService dbService;

    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        super.init();

        try {
            dbService.addScheme(UserDataSet.class);

            System.out.println("------------------------------------------------------");

            UserDataSet set1 = new UserDataSet();
            set1.setName("Ivan");
            set1.setAge(33);
            set1.setAddress("Russia");
            set1.setNumberOfChildren(2);

            UserDataSet set2 = new UserDataSet();
            set2.setName("Petr");
            set2.setAge(32);
            set2.setAddress("Armenia");
            set2.setNumberOfChildren(0);

            UserDataSet set3 = new UserDataSet();
            set3.setName("Ekaterina");
            set3.setAge(20);
            set3.setAddress("Russia");
            set3.setNumberOfChildren(0);

            dbService.save(set1);
            dbService.save(set2);
            dbService.save(set3);

            System.out.println("------------------------------------------------------");

            System.out.println(dbService.load(1, UserDataSet.class));
            System.out.println(dbService.load(2, UserDataSet.class));
            System.out.println(dbService.load(3, UserDataSet.class));
        } catch (SQLException e) {
            System.out.println(e);
        }
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
