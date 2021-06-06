DELETE FROM message;
INSERT INTO message (id, content, author, origin) VALUES
  (1, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:30:40'),
  (2, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:31:22'),
  (3, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Mac Afee', '2020-03-10 10:38:11'),
  (4, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Tony Stark', '2020-03-10 10:42:57');

/* encrypted password for id 1..4 is 1234 */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority) VALUES
  (1, 'Albert', 'Einstein', '86cabbed1f7d6042612de6e94c5a5555de54c24ee5c563dd91edaf4a0854e6ed1ced0a4de604b783', 'albert.einstein', 'admin'),
  (2, 'Mac',  'Afee', '86cabbed1f7d6042612de6e94c5a5555de54c24ee5c563dd91edaf4a0854e6ed1ced0a4de604b783', 'mac.afee', 'member'),
  (3, 'Tony',  'Stark', '86cabbed1f7d6042612de6e94c5a5555de54c24ee5c563dd91edaf4a0854e6ed1ced0a4de604b783', 'toni.stark', 'supervisor'),
  (4, 'Wilhelm',  'Tell', '86cabbed1f7d6042612de6e94c5a5555de54c24ee5c563dd91edaf4a0854e6ed1ced0a4de604b783', 'wilhelm.tell', 'member');
