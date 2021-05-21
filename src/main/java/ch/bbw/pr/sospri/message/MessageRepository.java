package ch.bbw.pr.sospri.message;

import org.springframework.data.repository.CrudRepository;

/**
 * Message Repository
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
//Klasse, id-Typ
public interface MessageRepository extends CrudRepository<Message, Long> {
    //Da wir eine embedded database verwenden, braucht es keine Conecction Information.
}

