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
        logger.trace("Constructor");
        this.repository = repository;

        if (!logger.isTraceEnabled()) {
            logger.info("Für detailliertere Informationen Log-Level 'Trace' aktivieren.");
        }
    }

    public Iterable<Member> getAll() {
        logger.trace("Method: getAll");
        return repository.findAll();
    }

    public void add(Member member) {
        logger.trace("Method: add");
        repository.save(member);
    }

    public void update(Member member, String authority) {
        logger.trace("Method: update(Member member, String authority)");

        if (!member.getAuthority().equals(authority)) {
            logger.trace("Method: update(Member member, String authority), Rolle wird verändert.");

            Object principal = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            logger.warn("Rolle von " + member.getUsername() + " wurde verändert durch " + ((UserDetails) principal).getUsername());
        } else {
            logger.trace("Method: update(Member member, String authority), Rolle bleibt gleich.");
        }

        member.setAuthority(authority);

        //save geht auch für update.
        repository.save(member);
    }

    public void update(Member member) {
        logger.trace("Method: update(Member member)");

        //save geht auch für update.
        repository.save(member);
    }

    public void deleteById(Long id) {
        logger.trace("Method: deleteById");
        repository.deleteById(id);
    }

    public Member getById(Long id) {
        logger.trace("Method: getById");
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getId() == id) {
                logger.trace("Method: getById, Entsprechender User wurde gefunden.");
                return member;
            }
        }

        logger.trace("Method: getById, Es wurde kein entsprechender User gefunden.");
        return null;
    }

    public Member getByUserName(String username) throws UsernameNotFoundException {
        logger.trace("Method: getByUserName");
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getUsername().equals(username)) {
                logger.trace("Method: getByUserName, Entsprechender User wurde gefunden.");
                return member;
            }
        }

        logger.trace("Es wurde kein entsprechender User gefunden.");
        throw new UsernameNotFoundException("Method: getByUserName, Benutzername konnte keinem Account zugeordnet werden.");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.trace("Method: loadUserByUsername");

        Member member = getByUserName(s);
        return MemberToUserDetailsMapper.toUserDetails(member);
    }
}
