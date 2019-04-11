package JavaLamps.ZipTube.models;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String url;

    private String fileName;

    private String title;

    private String description;

    private Date uploadDate;

    public Video() {
    }

    public Video(String idString) {
        this.id = Long.parseLong(idString);
    }

    public Video(String url, String fileName, String title, String description, Date uploadDate) {
        this.url = url;
        this.fileName = fileName;
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
