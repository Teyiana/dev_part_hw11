import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
    public static final String ZONE = "UTC";
    public static final String LAST_TIMEZONE = "lastTimezone";

    public static ZoneId getTimeZone(HttpServletRequest req) {
        String timezone = req.getParameter("timezone");
        if (timezone == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (LAST_TIMEZONE.equals(c.getName())) {
                        timezone = c.getValue();
                        break;
                    }
                }
            }
        }
        timezone = timezone == null ? ZONE : timezone;
        timezone = timezone.replace(' ', '+');
        return ZoneId.of(timezone);
    }
}
