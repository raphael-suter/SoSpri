package ch.bbw.pr.sospri.member;

import org.springframework.data.repository.CrudRepository;

/**
 * Member Repository
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
//Klasse, id-Typ
public interface MemberRepository extends CrudRepository<Member, Long> {
    //Da wir eine embedded database verwenden, braucht es keine Conecction Information.
}

