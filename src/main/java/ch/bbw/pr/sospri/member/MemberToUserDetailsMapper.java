package ch.bbw.pr.sospri.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class MemberToUserDetailsMapper {
    public static UserDetails toUserDetails(Member member) {
        User user = null;

        if (member != null) {
            java.util.Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));

            user = new User(member.getUsername(), member.getPassword(), true, true, true, true, authorities);
        }

        return user;
    }
}
