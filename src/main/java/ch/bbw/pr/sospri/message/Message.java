package ch.bbw.pr.sospri.message;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Message Entity
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(generator = "generatorMessage", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMessage", initialValue = 10)
    private Long id;

    @NotEmpty(message = "content may not be empty")
    @Size(min = 2, max = 512, message = "Die Länge der Message muss 2 bis 512 Zeichen sein.")
    private String content;

    //@NotEmpty (message = "author may not be empty" )
    @Size(min = 5, max = 20, message = "Die Länge des Autors 2 bis 20 Zeichen sein.")
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date origin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getOrigin() {
        return origin;
    }

    public void setOrigin(Date origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", content=" + content + ", author=" + author + ", origin=" + origin + "]";
    }
}
