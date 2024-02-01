package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "albums", schema = "main", catalog = "")
public class AlbumsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AlbumId")
    private int albumId;
    @Basic
    @Column(name = "Title")
    private Object title;
    @Basic
    @Column(name = "ArtistId")
    private int artistId;
    @ManyToOne
    @JoinColumn(name = "ArtistId", referencedColumnName = "ArtistId", nullable = false)
    private ArtistsEntity artistsByArtistId;
    @OneToMany(mappedBy = "albumsByAlbumId")
    private Collection<TracksEntity> tracksByAlbumId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumsEntity that = (AlbumsEntity) o;
        return albumId == that.albumId && artistId == that.artistId && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, title, artistId);
    }

    public ArtistsEntity getArtistsByArtistId() {
        return artistsByArtistId;
    }

    public void setArtistsByArtistId(ArtistsEntity artistsByArtistId) {
        this.artistsByArtistId = artistsByArtistId;
    }

    public Collection<TracksEntity> getTracksByAlbumId() {
        return tracksByAlbumId;
    }

    public void setTracksByAlbumId(Collection<TracksEntity> tracksByAlbumId) {
        this.tracksByAlbumId = tracksByAlbumId;
    }
}
