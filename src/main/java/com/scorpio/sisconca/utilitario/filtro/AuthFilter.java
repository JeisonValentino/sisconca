package com.scorpio.sisconca.utilitario.filtro;

import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebFilter(filterName = "AuthFilter", urlPatterns =
{
    "*.xhtml"
})
public class AuthFilter implements Filter
{

    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    public AuthFilter()
    {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        try
        {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            String reqURI = req.getRequestURI();
            LOG.info("Llega al doFilter");	
            if (reqURI.contains("/login.xhtml")
                    || (ses != null && ses.getAttribute(Faces.USER) != null)
                    || reqURI.contains("/public/")
                    || reqURI.contains("javax.faces.resource"))
            {

                chain.doFilter(request, response);

            } else
            {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } catch (IOException | ServletException t)
        {
            LOG.error("Mensaje de Error en Filtro: " + t.getMessage());
        }
    } // doFilter

    @Override
    public void destroy()
    {
    }
}
