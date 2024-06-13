import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init()  {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("./templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ZoneId zone = Util.getTimeZone(req);
        resp.setContentType("text/html; charset=utf-8");
        ZonedDateTime dateTime = ZonedDateTime.now(zone);
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Guest");
        data.put("time", dateTime.format(Util.dtf));
        Context simpleContext = new Context(req.getLocale(), data);
        String s = engine.process("time", simpleContext);
        resp.addCookie(new Cookie(Util.LAST_TIMEZONE, zone.getId()));
        resp.getWriter().write(s);
        resp.getWriter().close();
    }


}
