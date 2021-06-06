package ch.bbw.pr.sospri.member;

import org.springframework.security.core.GrantedAuthority;

public class MemberGrantedAuthority implements GrantedAuthority {
    private String authority;

    public MemberGrantedAuthority(String authority) {
        super();
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
