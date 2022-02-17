package tasks.interceptor;

import tasks.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

@Component
public class RestInterceptorAll extends HandlerInterceptorAdapter {

    @Value("${ms.auth.url}")
    private String authMsPath;

    @Value("${auth.url.validate}")
    private String authValidateUrl;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        if(req.getMethod().equals("OPTIONS")) return super.preHandle(req, res, handler);

        try {
            HttpHeaders headers = setAuthorizationHeader(req);
            HttpEntity<String> entity = new HttpEntity<>("headers", headers);
            User user = getUserInfoFromAuthService(entity);
            req.getSession().setAttribute("user", user);
            return super.preHandle(req, res, handler);
        } catch (Exception e) {
            // Users "access_token" is wrong so we should notify them that they're unauthorized (401)
            res.setStatus(401, "401 Unauthorized");
            // Return "false" so the "ValidateController" method isn't called
            return false;
        }
    }

    // Sets the "Authorization" header value (Authorization: Bearer ${access_token})
    private HttpHeaders setAuthorizationHeader(HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", req.getHeader("Authorization"));
        return headers;
    }

    // Sends a GET request to get the user info
    private User getUserInfoFromAuthService(HttpEntity<String> entity) {
        RestTemplate httpRequest = new RestTemplate();
        return httpRequest.exchange(
                authMsPath.concat(authValidateUrl),
                HttpMethod.GET,
                entity,
                User.class
        ).getBody();
    }

}
