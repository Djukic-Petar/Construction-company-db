/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Magacin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magacin.findAll", query = "SELECT m FROM Magacin m")
    , @NamedQuery(name = "Magacin.findByIdMagacin", query = "SELECT m FROM Magacin m WHERE m.idMagacin = :idMagacin")
    , @NamedQuery(name = "Magacin.findByPlata", query = "SELECT m FROM Magacin m WHERE m.plata = :plata")})
public class Magacin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idMagacin")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idMagacin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Plata")
    private BigDecimal plata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMagacin")
    private Collection<Sadrzi> sadrziCollection;
    @JoinColumn(name = "idGradiliste", referencedColumnName = "idGradiliste")
    @ManyToOne(optional = false)
    private Gradiliste idGradiliste;
    @JoinColumn(name = "idSef", referencedColumnName = "idZaposleni")
    @ManyToOne(optional = false)
    private Zaposleni idSef;
    @OneToMany(mappedBy = "idMagacin")
    private Collection<Zaposleni> zaposleniCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMagacin")
    private Collection<Zaduzenje> zaduzenjeCollection;

    public Magacin() {
    }

    public Magacin(Integer idMagacin) {
        this.idMagacin = idMagacin;
    }

    public Integer getIdMagacin() {
        return idMagacin;
    }

    public void setIdMagacin(Integer idMagacin) {
        this.idMagacin = idMagacin;
    }

    public BigDecimal getPlata() {
        return plata;
    }

    public void setPlata(BigDecimal plata) {
        this.plata = plata;
    }

    @XmlTransient
    public Collection<Sadrzi> getSadrziCollection() {
        return sadrziCollection;
    }

    public void setSadrziCollection(Collection<Sadrzi> sadrziCollection) {
        this.sadrziCollection = sadrziCollection;
    }

    public Gradiliste getIdGradiliste() {
        return idGradiliste;
    }

    public void setIdGradiliste(Gradiliste idGradiliste) {
        this.idGradiliste = idGradiliste;
    }

    public Zaposleni getIdSef() {
        return idSef;
    }

    public void setIdSef(Zaposleni idSef) {
        this.idSef = idSef;
    }

    @XmlTransient
    public Collection<Zaposleni> getZaposleniCollection() {
        return zaposleniCollection;
    }

    public void setZaposleniCollection(Collection<Zaposleni> zaposleniCollection) {
        this.zaposleniCollection = zaposleniCollection;
    }

    @XmlTransient
    public Collection<Zaduzenje> getZaduzenjeCollection() {
        return zaduzenjeCollection;
    }

    public void setZaduzenjeCollection(Collection<Zaduzenje> zaduzenjeCollection) {
        this.zaduzenjeCollection = zaduzenjeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMagacin != null ? idMagacin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magacin)) {
            return false;
        }
        Magacin other = (Magacin) object;
        if ((this.idMagacin == null && other.idMagacin != null) || (this.idMagacin != null && !this.idMagacin.equals(other.idMagacin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Magacin[ idMagacin=" + idMagacin + " ]";
    }
    
    public static Magacin findById(EntityManager em, int idMagacin) {
        TypedQuery<Magacin> query = em.createNamedQuery("Magacin.findByIdMagacin", Magacin.class);
        query.setParameter("idMagacin", idMagacin);
        return query.getSingleResult();
    }
    
    public static List<Magacin> findAll(EntityManager em) {
        TypedQuery<Magacin> query = em.createNamedQuery("Magacin.findAll", Magacin.class);
        List<Magacin> results = query.getResultList();
        return results;
    }
    
}
