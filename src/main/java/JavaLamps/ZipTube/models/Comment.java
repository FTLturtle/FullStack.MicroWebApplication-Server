package JavaLamps.ZipTube.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.swing.plaf.synth.Region;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String body;

    private String posterName;

    private Date postDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Video video;

    public Comment() {
    }


    public Comment(String body, String posterName, Date postDate, Video video) {
        this.body = body;
        this.posterName = posterName;
        this.postDate = postDate;
        this.video = video;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
