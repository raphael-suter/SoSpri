package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PasswordExpirationFilter implements Filter {
    private final MemberService memberService;

    public PasswordExpirationFilter(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Member currentlyLoggedIn = getMember();

        String url = httpRequest
                .getRequestURL()
                .toString();

        if (passwordExpirationCheckNecessary(url) && currentlyLoggedIn != null && currentlyLoggedIn.isPasswordExpired()) {
            redirect(httpRequest, httpResponse, "/change-password");
            return;
        }

        chain.doFilter(request, response);
    }

    private void redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String to) {
        try {
            String redirectURL = httpServletRequest.getContextPath() + to;
            httpServletResponse.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Member getMember() {
        Member tmpMember = null;

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            tmpMember = memberService.getByUserName(((UserDetails) principal).getUsername());
        }

        return tmpMember;
    }

    private boolean passwordExpirationCheckNecessary(String url) {
        if (url.contains("/h2-console/")
                || url.contains("/css/")
                || url.contains("/fragments/")
                || url.contains("/img/")
                || url.contains("/403.html")
                || url.contains("/404.html")
                || url.contains("/contact.html")
                || url.contains("/index.html")
                || url.contains("/login")
                || url.contains("/get-register")
                || url.contains("/change-password")
                || url.contains("/logout-page")) {
            return false;
        }

        return true;
    }
}