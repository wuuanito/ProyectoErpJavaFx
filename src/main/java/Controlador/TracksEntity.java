package Controlador;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tracks", schema = "main", catalog = "")
public class TracksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TrackId")
    private int trackId;
    @Basic
    @Column(name = "Name")
    private Object name;
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
    private Object composer;
    @Basic
    @Column(name = "Milliseconds")
    private int milliseconds;
    @Basic
    @Column(name = "Bytes")
    private Integer bytes;
    @Basic
    @Column(name = "UnitPrice")
    private Date unitPrice;
    @OneToMany(mappedBy = "tracksByTrackId")
    private Collection<InvoiceItemsEntity> invoiceItemsByTrackId;
    @OneToMany(mappedBy = "tracksByTrackId")
    private Collection<PlaylistTrackEntity> playlistTracksByTrackId;
    @ManyToOne
    @JoinColumn(name = "AlbumId", referencedColumnName = "AlbumId")
    private AlbumsEntity albumsByAlbumId;
    @ManyToOne
    @JoinColumn(name = "MediaTypeId", referencedColumnName = "MediaTypeId", nullable = false)
    private MediaTypesEntity mediaTypesByMediaTypeId;
    @ManyToOne
    @JoinColumn(name = "GenreId", referencedColumnName = "GenreId")
    private GenresEntity genresByGenreId;

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
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

    public Object getComposer() {
        return composer;
    }

    public void setComposer(Object composer) {
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

    public Date getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Date unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TracksEntity that = (TracksEntity) o;
        return trackId == that.trackId && mediaTypeId == that.mediaTypeId && milliseconds == that.milliseconds && Objects.equals(name, that.name) && Objects.equals(albumId, that.albumId) && Objects.equals(genreId, that.genreId) && Objects.equals(composer, that.composer) && Objects.equals(bytes, that.bytes) && Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, name, albumId, mediaTypeId, genreId, composer, milliseconds, bytes, unitPrice);
    }

    public Collection<InvoiceItemsEntity> getInvoiceItemsByTrackId() {
        return invoiceItemsByTrackId;
    }

    public void setInvoiceItemsByTrackId(Collection<InvoiceItemsEntity> invoiceItemsByTrackId) {
        this.invoiceItemsByTrackId = invoiceItemsByTrackId;
    }

    public Collection<PlaylistTrackEntity> getPlaylistTracksByTrackId() {
        return playlistTracksByTrackId;
    }

    public void setPlaylistTracksByTrackId(Collection<PlaylistTrackEntity> playlistTracksByTrackId) {
        this.playlistTracksByTrackId = playlistTracksByTrackId;
    }

    public AlbumsEntity getAlbumsByAlbumId() {
        return albumsByAlbumId;
    }

    public void setAlbumsByAlbumId(AlbumsEntity albumsByAlbumId) {
        this.albumsByAlbumId = albumsByAlbumId;
    }

    public MediaTypesEntity getMediaTypesByMediaTypeId() {
        return mediaTypesByMediaTypeId;
    }

    public void setMediaTypesByMediaTypeId(MediaTypesEntity mediaTypesByMediaTypeId) {
        this.mediaTypesByMediaTypeId = mediaTypesByMediaTypeId;
    }

    public GenresEntity getGenresByGenreId() {
        return genresByGenreId;
    }

    public void setGenresByGenreId(GenresEntity genresByGenreId) {
        this.genresByGenreId = genresByGenreId;
    }
}
