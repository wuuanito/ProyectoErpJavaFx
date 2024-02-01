package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "media_types", schema = "main", catalog = "")
public class MediaTypesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MediaTypeId")
    private int mediaTypeId;
    @Basic
    @Column(name = "Name")
    private Object name;
    @OneToMany(mappedBy = "mediaTypesByMediaTypeId")
    private Collection<TracksEntity> tracksByMediaTypeId;

    public int getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(int mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaTypesEntity that = (MediaTypesEntity) o;
        return mediaTypeId == that.mediaTypeId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaTypeId, name);
    }

    public Collection<TracksEntity> getTracksByMediaTypeId() {
        return tracksByMediaTypeId;
    }

    public void setTracksByMediaTypeId(Collection<TracksEntity> tracksByMediaTypeId) {
        this.tracksByMediaTypeId = tracksByMediaTypeId;
    }
}
