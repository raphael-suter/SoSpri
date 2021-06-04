package ch.bbw.pr.sospri.member;

import org.springframework.data.repository.CrudRepository;

/**
 * Member Repository
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
}

