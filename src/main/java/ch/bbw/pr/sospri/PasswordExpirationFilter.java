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

        if (isUrlExcluded(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member tmpMember = null;

        if (principal instanceof UserDetails) {
            tmpMember = memberService.getByUserName(((UserDetails) principal).getUsername());
        }

        if (tmpMember != null && tmpMember.isPasswordExpired()) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String redirectURL = httpRequest.getContextPath() + "/change-password";
            httpResponse.sendRedirect(redirectURL);
        } else {
            chain.doFilter(httpRequest, response);
        }

    }

    private boolean isUrlExcluded(HttpServletRequest httpRequest) throws IOException, ServletException {
        String url = httpRequest.getRequestURL().toString();

        if (url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".js") || url.endsWith("/change-password")) {
            return true;
        }

        return false;
    }
}