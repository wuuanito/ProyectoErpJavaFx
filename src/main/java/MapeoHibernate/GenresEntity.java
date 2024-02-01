package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "genres", schema = "main", catalog = "")
public class GenresEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "GenreId")
    private int genreId;
    @Basic
    @Column(name = "Name")
    private Object name;
    @OneToMany(mappedBy = "genresByGenreId")
    private Collection<TracksEntity> tracksByGenreId;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
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
        GenresEntity that = (GenresEntity) o;
        return genreId == that.genreId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, name);
    }

    public Collection<TracksEntity> getTracksByGenreId() {
        return tracksByGenreId;
    }

    public void setTracksByGenreId(Collection<TracksEntity> tracksByGenreId) {
        this.tracksByGenreId = tracksByGenreId;
    }
}
