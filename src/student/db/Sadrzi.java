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
@Table(name = "Sadrzi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sadrzi.findAll", query = "SELECT s FROM Sadrzi s")
    , @NamedQuery(name = "Sadrzi.findByRobaAndMagacin", query = "SELECT s FROM Sadrzi s WHERE s.idMagacin = :idMagacin AND s.idRoba = :idRoba")
    , @NamedQuery(name = "Sadrzi.findByKolicina", query = "SELECT s FROM Sadrzi s WHERE s.kolicina = :kolicina")
    , @NamedQuery(name = "Sadrzi.findByIdSadrzi", query = "SELECT s FROM Sadrzi s WHERE s.idSadrzi = :idSadrzi")})
public class Sadrzi implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Kolicina")
    private BigDecimal kolicina;
    @Column(name = "brojJedinica")
    private Integer brojJedinica;
    @Id
    @Basic(optional = false)
    @Column(name = "idSadrzi")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSadrzi;
    @JoinColumn(name = "idMagacin", referencedColumnName = "idMagacin")
    @ManyToOne(optional = false)
    private Magacin idMagacin;
    @JoinColumn(name = "idRoba", referencedColumnName = "idRoba")
    @ManyToOne(optional = false)
    private Roba idRoba;

    public Sadrzi() {
    }

    public Sadrzi(Integer idSadrzi) {
        this.idSadrzi = idSadrzi;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public Integer getBrojJedinica() {
        return brojJedinica;
    }

    public void setBrojJedinica(Integer brojJedinica) {
        this.brojJedinica = brojJedinica;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getIdSadrzi() {
        return idSadrzi;
    }

    public void setIdSadrzi(Integer idSadrzi) {
        this.idSadrzi = idSadrzi;
    }

    public Magacin getIdMagacin() {
        return idMagacin;
    }

    public void setIdMagacin(Magacin idMagacin) {
        this.idMagacin = idMagacin;
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
        hash += (idSadrzi != null ? idSadrzi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sadrzi)) {
            return false;
        }
        Sadrzi other = (Sadrzi) object;
        if ((this.idSadrzi == null && other.idSadrzi != null) || (this.idSadrzi != null && !this.idSadrzi.equals(other.idSadrzi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Sadrzi[ idSadrzi=" + idSadrzi + " ]";
    }
    
    public static Sadrzi findByRobaAndMagacin(EntityManager em, int idRoba, int idMagacin) {
        TypedQuery<Sadrzi> query = em.createNamedQuery("Sadrzi.findByRobaAndMagacin", Sadrzi.class);
        query.setParameter("idRoba", Roba.findById(em, idRoba));
        query.setParameter("idMagacin", Magacin.findById(em, idMagacin));
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
