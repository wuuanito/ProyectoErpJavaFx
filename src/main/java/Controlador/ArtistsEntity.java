package Controlador;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "artists", schema = "main", catalog = "")
public class ArtistsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ArtistId")
    private int artistId;
    @Basic
    @Column(name = "Name")
    private Object name;
    @OneToMany(mappedBy = "artistsByArtistId")
    private Collection<AlbumsEntity> albumsByArtistId;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
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
        ArtistsEntity that = (ArtistsEntity) o;
        return artistId == that.artistId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, name);
    }

    public Collection<AlbumsEntity> getAlbumsByArtistId() {
        return albumsByArtistId;
    }

    public void setAlbumsByArtistId(Collection<AlbumsEntity> albumsByArtistId) {
        this.albumsByArtistId = albumsByArtistId;
    }
}
