package app.sonarcloud.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClaseDeNegocio entity.
 */
public class ClaseDeNegocioDTO implements Serializable {

    private Long id;

    private String unCampo;

    private String otroCampo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnCampo() {
        return unCampo;
    }

    public void setUnCampo(String unCampo) {
        this.unCampo = unCampo;
    }

    public String getOtroCampo() {
        return otroCampo;
    }

    public void setOtroCampo(String otroCampo) {
        this.otroCampo = otroCampo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClaseDeNegocioDTO claseDeNegocioDTO = (ClaseDeNegocioDTO) o;
        if (claseDeNegocioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claseDeNegocioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClaseDeNegocioDTO{" +
            "id=" + getId() +
            ", unCampo='" + getUnCampo() + "'" +
            ", otroCampo='" + getOtroCampo() + "'" +
            "}";
    }
}
