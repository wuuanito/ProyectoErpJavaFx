package MapeoHibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "playlist_track", schema = "main", catalog = "")
@IdClass(PlaylistTrackEntityPK.class)
public class PlaylistTrackEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PlaylistId")
    private int playlistId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TrackId")
    private int trackId;
    @ManyToOne
    @JoinColumn(name = "PlaylistId", referencedColumnName = "PlaylistId", nullable = false)
    private PlaylistsEntity playlistsByPlaylistId;
    @ManyToOne
    @JoinColumn(name = "TrackId", referencedColumnName = "TrackId", nullable = false)
    private TracksEntity tracksByTrackId;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistTrackEntity that = (PlaylistTrackEntity) o;
        return playlistId == that.playlistId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, trackId);
    }

    public PlaylistsEntity getPlaylistsByPlaylistId() {
        return playlistsByPlaylistId;
    }

    public void setPlaylistsByPlaylistId(PlaylistsEntity playlistsByPlaylistId) {
        this.playlistsByPlaylistId = playlistsByPlaylistId;
    }

    public TracksEntity getTracksByTrackId() {
        return tracksByTrackId;
    }

    public void setTracksByTrackId(TracksEntity tracksByTrackId) {
        this.tracksByTrackId = tracksByTrackId;
    }
}
