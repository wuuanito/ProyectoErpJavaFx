package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "playlists", schema = "main", catalog = "")
public class PlaylistsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PlaylistId")
    private int playlistId;
    @Basic
    @Column(name = "Name")
    private Object name;
    @OneToMany(mappedBy = "playlistsByPlaylistId")
    private Collection<PlaylistTrackEntity> playlistTracksByPlaylistId;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
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
        PlaylistsEntity that = (PlaylistsEntity) o;
        return playlistId == that.playlistId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, name);
    }

    public Collection<PlaylistTrackEntity> getPlaylistTracksByPlaylistId() {
        return playlistTracksByPlaylistId;
    }

    public void setPlaylistTracksByPlaylistId(Collection<PlaylistTrackEntity> playlistTracksByPlaylistId) {
        this.playlistTracksByPlaylistId = playlistTracksByPlaylistId;
    }
}
