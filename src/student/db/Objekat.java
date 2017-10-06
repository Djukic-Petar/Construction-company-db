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
@Table(name = "Objekat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objekat.findAll", query = "SELECT o FROM Objekat o")
    , @NamedQuery(name = "Objekat.findByIdObjekat", query = "SELECT o FROM Objekat o WHERE o.idObjekat = :idObjekat")
    , @NamedQuery(name = "Objekat.findByNaziv", query = "SELECT o FROM Objekat o WHERE o.naziv = :naziv")
    , @NamedQuery(name = "Objekat.findByBrojSpratova", query = "SELECT o FROM Objekat o WHERE o.brojSpratova = :brojSpratova")
    , @NamedQuery(name = "Objekat.findNajvisiSprat", query = "SELECT COALESCE(MAX(s.brojSprata), -1) FROM Sprat s WHERE s.idObjekat = :idObjekat")})
public class Objekat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idObjekat")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idObjekat;
    @Column(name = "Naziv")
    private String naziv;
    @Column(name = "BrojSpratova")
    private Integer brojSpratova;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idObjekat")
    private Collection<Sprat> spratCollection;
    @JoinColumn(name = "idGradiliste", referencedColumnName = "idGradiliste")
    @ManyToOne(optional = false)
    private Gradiliste idGradiliste;

    public Objekat() {
    }

    public Objekat(Integer idObjekat) {
        this.idObjekat = idObjekat;
    }

    public Integer getIdObjekat() {
        return idObjekat;
    }

    public void setIdObjekat(Integer idObjekat) {
        this.idObjekat = idObjekat;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojSpratova() {
        return brojSpratova;
    }

    public void setBrojSpratova(Integer brojSpratova) {
        this.brojSpratova = brojSpratova;
    }

    @XmlTransient
    public Collection<Sprat> getSpratCollection() {
        return spratCollection;
    }

    public void setSpratCollection(Collection<Sprat> spratCollection) {
        this.spratCollection = spratCollection;
    }

    public Gradiliste getIdGradiliste() {
        return idGradiliste;
    }

    public void setIdGradiliste(Gradiliste idGradiliste) {
        this.idGradiliste = idGradiliste;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObjekat != null ? idObjekat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objekat)) {
            return false;
        }
        Objekat other = (Objekat) object;
        if ((this.idObjekat == null && other.idObjekat != null) || (this.idObjekat != null && !this.idObjekat.equals(other.idObjekat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Objekat[ idObjekat=" + idObjekat + " ]";
    }
    
    public static Objekat findById(EntityManager em, int idObjekat) {
        TypedQuery<Objekat> query = em.createNamedQuery("Objekat.findByIdObjekat", Objekat.class);
        query.setParameter("idObjekat", idObjekat);
        return query.getSingleResult();
    }
    
    public static int findNajvisiSprat(EntityManager em, int idObjekat) {
        TypedQuery<Integer> query = em.createNamedQuery("Objekat.findNajvisiSprat", Integer.class);
        query.setParameter("idObjekat", Objekat.findById(em, idObjekat));
        return query.getSingleResult();
    }
    
}
