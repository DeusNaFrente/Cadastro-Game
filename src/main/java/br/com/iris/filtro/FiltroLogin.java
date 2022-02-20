package br.com.iris.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class FiltroLogin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        HttpSession session = hreq.getSession();
        if (isAjax(hreq) && !hreq.isRequestedSessionIdValid()) {  
            hres.getWriter().print(xmlPartialRedirectToPage(hreq, "/index"));  
            hres.flushBuffer();  
        }else {  
            if (session.getAttribute("logado") == null) {
                String url = hreq.getContextPath();
                hres.sendRedirect(url + "/login.ud"); 
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private boolean isAjax(HttpServletRequest request) {  
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    } 
    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {  
        StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
        sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(request.getServletPath()).append(page).append("\"/></partial-response>");  
        return sb.toString();  
    }  
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
