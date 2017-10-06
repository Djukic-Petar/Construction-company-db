/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "MaterijalZaNormu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaterijalZaNormu.findAll", query = "SELECT m FROM MaterijalZaNormu m")
    , @NamedQuery(name = "Sadrzi.findByRobaAndNorma", query = "SELECT m FROM MaterijalZaNormu m WHERE m.idNorma = :idNorma AND m.idRoba = :idRoba")
    , @NamedQuery(name = "MaterijalZaNormu.findByIdMaterijalZaNormu", query = "SELECT m FROM MaterijalZaNormu m WHERE m.idMaterijalZaNormu = :idMaterijalZaNormu")
    , @NamedQuery(name = "MaterijalZaNormu.findByKolicina", query = "SELECT m FROM MaterijalZaNormu m WHERE m.kolicina = :kolicina")})
public class MaterijalZaNormu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idMaterijalZaNormu")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idMaterijalZaNormu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Kolicina")
    private BigDecimal kolicina;
    @Column(name = "brojJedinica")
    private Integer brojJedinica;
    @JoinColumn(name = "idNorma", referencedColumnName = "idNorma")
    @ManyToOne(optional = false)
    private Norma idNorma;
    @JoinColumn(name = "idRoba", referencedColumnName = "idRoba")
    @ManyToOne(optional = false)
    private Roba idRoba;

    public MaterijalZaNormu() {
    }

    public MaterijalZaNormu(Integer idMaterijalZaNormu) {
        this.idMaterijalZaNormu = idMaterijalZaNormu;
    }

    public Integer getIdMaterijalZaNormu() {
        return idMaterijalZaNormu;
    }

    public void setIdMaterijalZaNormu(Integer idMaterijalZaNormu) {
        this.idMaterijalZaNormu = idMaterijalZaNormu;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getBrojJedinica() {
        return brojJedinica;
    }

    public void setBrojJedinica(Integer brojJedinica) {
        this.brojJedinica = brojJedinica;
    }

    public Norma getIdNorma() {
        return idNorma;
    }

    public void setIdNorma(Norma idNorma) {
        this.idNorma = idNorma;
    }

    public Roba getIdRoba() {
        return idRoba;
    }

    public void setIdRoba(Roba idRoba) {
        this.idRoba = idRoba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaterijalZaNormu != null ? idMaterijalZaNormu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterijalZaNormu)) {
            return false;
        }
        MaterijalZaNormu other = (MaterijalZaNormu) object;
        if ((this.idMaterijalZaNormu == null && other.idMaterijalZaNormu != null) || (this.idMaterijalZaNormu != null && !this.idMaterijalZaNormu.equals(other.idMaterijalZaNormu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.MaterijalZaNormu[ idMaterijalZaNormu=" + idMaterijalZaNormu + " ]";
    }

    public static MaterijalZaNormu findByRobaAndNorma(EntityManager em, int idRoba, int idNorma) {
        TypedQuery<MaterijalZaNormu> query = em.createNamedQuery("Sadrzi.findByRobaAndNorma", MaterijalZaNormu.class);
        query.setParameter("idRoba", idRoba);
        query.setParameter("idNorma", idNorma);
        return query.getSingleResult();
    }
}
