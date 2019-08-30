package app.sonarcloud.test.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClaseDeNegocio.
 */
@Entity
@Table(name = "clase_de_negocio")
public class ClaseDeNegocio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "un_campo")
    private String unCampo;

    @Column(name = "otro_campo")
    private String otroCampo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnCampo() {
        return unCampo;
    }

    public ClaseDeNegocio unCampo(String unCampo) {
        this.unCampo = unCampo;
        return this;
    }

    public void setUnCampo(String unCampo) {
        this.unCampo = unCampo;
    }

    public String getOtroCampo() {
        return otroCampo;
    }

    public ClaseDeNegocio otroCampo(String otroCampo) {
        this.otroCampo = otroCampo;
        return this;
    }

    public void setOtroCampo(String otroCampo) {
        this.otroCampo = otroCampo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClaseDeNegocio claseDeNegocio = (ClaseDeNegocio) o;
        if (claseDeNegocio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claseDeNegocio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClaseDeNegocio{" +
            "id=" + getId() +
            ", unCampo='" + getUnCampo() + "'" +
            ", otroCampo='" + getOtroCampo() + "'" +
            "}";
    }
}
