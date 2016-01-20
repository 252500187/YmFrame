package com.servlet;

import com.server.bean.BeanFactory;
import com.server.classes.ClassesFactory;
import com.server.vo.ControllerData;
import com.server.vo.ControllerMethod;
import com.server.vo.ControllerView;
import com.userDefine.LogDefine;
import com.util.JsonUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
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
            String urlContexts = null;
            InputStream inputStream = request.getInputStream();
            //2432 bytes
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1) {
                urlContexts += new String(buffer, "utf-8");
            }
            inputStream.close();
            for (String urlContent : urlContexts.split("&")) {
                String content[] = urlContent.split("=");
                params.put(content[0], content[1]);
            }
            Object result = null;
            try {
                result = controllerMethod.getMethod().invoke(controllerMethod.getObject(), params);
            } catch (Exception e) {
                logger.error(LogDefine.getErrorLog("Servlet Invoke ControllerMethod", controllerMethod.getMethod().getName(), e));
            }
            if (result instanceof ControllerView) {
//                response.sendRedirect("");
//                request.getRequestDispatcher("");
            } else if (result instanceof String) {
                response.sendRedirect((String) result);
            } else if (result instanceof ControllerData) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JsonUtil.toJson(result));
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }
}
