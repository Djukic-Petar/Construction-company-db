/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
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
@Table(name = "Roba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roba.findAllIds", query = "SELECT r.idRoba FROM Roba r")
    , @NamedQuery(name = "Roba.findByIdRoba", query = "SELECT r FROM Roba r WHERE r.idRoba = :idRoba")
    , @NamedQuery(name = "Roba.findByNaziv", query = "SELECT r FROM Roba r WHERE r.naziv = :naziv")
    , @NamedQuery(name = "Roba.findByKod", query = "SELECT r FROM Roba r WHERE r.kod = :kod")})
public class Roba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idRoba")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idRoba;
    @Column(name = "Naziv")
    private String naziv;
    @Column(name = "Kod")
    private String kod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRoba")
    private Collection<Sadrzi> sadrziCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRoba")
    private Collection<MaterijalZaNormu> materijalZaNormuCollection;
    @JoinColumn(name = "idTip", referencedColumnName = "idTip")
    @ManyToOne(optional = false)
    private Tip idTip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRoba")
    private Collection<Zaduzenje> zaduzenjeCollection;

    public Roba() {
    }

    public Roba(Integer idRoba) {
        this.idRoba = idRoba;
    }

    public Integer getIdRoba() {
        return idRoba;
    }

    public void setIdRoba(Integer idRoba) {
        this.idRoba = idRoba;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    @XmlTransient
    public Collection<Sadrzi> getSadrziCollection() {
        return sadrziCollection;
    }

    public void setSadrziCollection(Collection<Sadrzi> sadrziCollection) {
        this.sadrziCollection = sadrziCollection;
    }

    @XmlTransient
    public Collection<MaterijalZaNormu> getMaterijalZaNormuCollection() {
        return materijalZaNormuCollection;
    }

    public void setMaterijalZaNormuCollection(Collection<MaterijalZaNormu> materijalZaNormuCollection) {
        this.materijalZaNormuCollection = materijalZaNormuCollection;
    }

    public Tip getIdTip() {
        return idTip;
    }

    public void setIdTip(Tip idTip) {
        this.idTip = idTip;
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
        hash += (idRoba != null ? idRoba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roba)) {
            return false;
        }
        Roba other = (Roba) object;
        if ((this.idRoba == null && other.idRoba != null) || (this.idRoba != null && !this.idRoba.equals(other.idRoba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Roba[ idRoba=" + idRoba + " ]";
    }
    
    public static Roba findById(EntityManager em, int idRoba) {
        TypedQuery<Roba> query = em.createNamedQuery("Roba.findByIdRoba", Roba.class);
        query.setParameter("idRoba", idRoba);
        return query.getSingleResult();
    }
    
    public static List<Integer> findAllIds(EntityManager em) {
        TypedQuery<Integer> query = em.createNamedQuery("Roba.findAllIds", Integer.class);
        List<Integer> results = query.getResultList();
        return results;
    }
    
}
