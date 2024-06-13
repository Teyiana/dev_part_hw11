import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;



@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
        try {
            ZoneId ignore = Util.getTimeZone((HttpServletRequest) req);
            chain.doFilter(req, res);
        } catch (DateTimeException e) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setStatus(400);
            response.setContentType(" ");
            response.getWriter().write("{\"Error\": \"Invalid timezone\"}");
            response.getWriter().close();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
