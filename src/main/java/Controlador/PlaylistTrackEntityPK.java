package Controlador;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PlaylistTrackEntityPK implements Serializable {
    @Column(name = "PlaylistId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playlistId;
    @Column(name = "TrackId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackId;

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
        PlaylistTrackEntityPK that = (PlaylistTrackEntityPK) o;
        return playlistId == that.playlistId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, trackId);
    }
}
