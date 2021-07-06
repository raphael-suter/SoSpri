package ch.bbw.pr.sospri.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member Service
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Service
@Transactional
public class MemberService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Iterable<Member> getAll() {
        return repository.findAll();
    }

    public void add(Member member) {
        repository.save(member);
    }

    public void update(Member member, String authority) {

        if (!member.getAuthority().equals(authority)) {
            logger.trace("Rolle wird verändert.");

            Object principal = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            logger.warn("Rolle von " + member.getUsername() + " wurde verändert durch " + ((UserDetails) principal).getUsername());
        }

        member.setAuthority(authority);

        //save geht auch für update.
        repository.save(member);
    }

    public void update(Member member) {
        //save geht auch für update.
        repository.save(member);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Member getById(Long id) {
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getId() == id) {
                return member;
            }
        }

        return null;
    }

    public Member getByUserName(String username) throws UsernameNotFoundException {
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }

        throw new UsernameNotFoundException("Benutzername konnte keinem Account zugeordnet werden.");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = getByUserName(s);
        return MemberToUserDetailsMapper.toUserDetails(member);
    }


}
