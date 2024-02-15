package Aplicacion;

import javax.persistence.*;

@Entity
@Table(name = "artist", schema = "chinook", catalog = "")
public class ArtistClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ArtistId")
    private int artistId;
    @Basic
    @Column(name = "Name")
    private String name;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
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

        ArtistClass that = (ArtistClass) o;

        if (artistId != that.artistId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = artistId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
