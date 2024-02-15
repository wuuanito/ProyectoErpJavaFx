package Aplicacion;

import javax.persistence.*;

@Entity
@Table(name = "mediatype", schema = "chinook", catalog = "")
public class MediatypeClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MediaTypeId")
    private int mediaTypeId;
    @Basic
    @Column(name = "Name")
    private String name;

    public int getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(int mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediatypeClass that = (MediatypeClass) o;

        if (mediaTypeId != that.mediaTypeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mediaTypeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
