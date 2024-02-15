package Aplicacion;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "track", schema = "chinook", catalog = "")
public class TrackClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TrackId")
    private int trackId;
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "AlbumId")
    private Integer albumId;
    @Basic
    @Column(name = "MediaTypeId")
    private int mediaTypeId;
    @Basic
    @Column(name = "GenreId")
    private Integer genreId;
    @Basic
    @Column(name = "Composer")
    private String composer;
    @Basic
    @Column(name = "Milliseconds")
    private int milliseconds;
    @Basic
    @Column(name = "Bytes")
    private Integer bytes;
    @Basic
    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public int getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(int mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackClass that = (TrackClass) o;

        if (trackId != that.trackId) return false;
        if (mediaTypeId != that.mediaTypeId) return false;
        if (milliseconds != that.milliseconds) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (albumId != null ? !albumId.equals(that.albumId) : that.albumId != null) return false;
        if (genreId != null ? !genreId.equals(that.genreId) : that.genreId != null) return false;
        if (composer != null ? !composer.equals(that.composer) : that.composer != null) return false;
        if (bytes != null ? !bytes.equals(that.bytes) : that.bytes != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trackId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (albumId != null ? albumId.hashCode() : 0);
        result = 31 * result + mediaTypeId;
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        result = 31 * result + (composer != null ? composer.hashCode() : 0);
        result = 31 * result + milliseconds;
        result = 31 * result + (bytes != null ? bytes.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        return result;
    }
}
