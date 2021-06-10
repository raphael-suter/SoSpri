package ch.bbw.pr.sospri.member;

import javax.persistence.*;

/**
 * Member Entity
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Entity
@Table(name = "member")
public class Member {
    private static final long PASSWORD_EXPIRATION_TIME = 60 * 1000;
    private static final long LOGIN_EXPIRATION_TIME = 2 * 60 * 1000;

    @Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue = 20)
    private Long id;

    private String prename;
    private String lastname;
    private String password;
    private String username;
    private String authority;
    private long pw_changed;
    private long login_expired;

    public Member(String prename, String lastname, String password, String username, String authority, long pw_changed) {
        this.prename = prename;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
        this.authority = authority;
        this.pw_changed = pw_changed;
    }

    public Member() {
        super();
    }

    public boolean isPasswordExpired() {
        if (this.pw_changed == 0) return false;

        long currentTime = System.currentTimeMillis();
        long lastChangedTime = this.pw_changed;

        return currentTime > lastChangedTime + PASSWORD_EXPIRATION_TIME;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getPw_changed() {
        return pw_changed;
    }

    public void setPw_changed(long pw_changed) {
        this.pw_changed = pw_changed;
    }

    public long getLogin_expired() {
        return login_expired;
    }

    public void setLogin_expired(long login_expired) {
        this.login_expired = login_expired;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", prename='" + prename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                ", pw_changed=" + pw_changed +
                ", login_expired=" + login_expired +
                '}';
    }
}
