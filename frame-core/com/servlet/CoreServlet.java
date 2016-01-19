package com.servlet;

import com.server.vo.ControllerMethod;
import org.apache.log4j.Logger;
import com.server.bean.BeanFactory;
import com.server.classes.ClassesFactory;
import com.userDefine.LogDefine;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijunbo on 2016/1/12.
 */
public class CoreServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CoreServlet.class);

    @Override
    public void init(ServletConfig servletConfig) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(BeanFactory.class.getName());
            Thread.currentThread().getContextClassLoader().loadClass(ClassesFactory.class.getName());
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("HttpServlet init", "", e));
        }
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ControllerMethod controllerMethod = BeanFactory.getControllerActionMethod(request.getPathInfo(), request.getMethod().trim().toLowerCase());
        if (controllerMethod != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            Enumeration<String> requestParams = request.getParameterNames();
            while (requestParams.hasMoreElements()) {
                String paramName = requestParams.nextElement();
                params.put(paramName, request.getParameter(paramName));
            }
            String urlContexts = request.getInputStream().toString();
            for (String urlContent : urlContexts.split("&")) {
                String content[] = urlContent.split("=");
                params.put(content[0], content[1]);
            }
//
//
//            result = invok();
//
//            if (result) {
//                response.sendRedirect("");
//                request.getRequestDispatcher("").forward("");
//            } else if (result) {
//                response.setContentType("application/json");
//                response.setCharacterEncoding("UTF-8");
//                response.getWriter().write(result.toJson);
//                response.getWriter().flush();
//                response.getWriter().close();
//            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println(-1);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter printWriter = response.getWriter();
        printWriter.write("hehe");
        System.out.println(1);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(2);
    }
}
