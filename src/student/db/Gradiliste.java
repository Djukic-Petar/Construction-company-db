package student.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Gradiliste")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gradiliste.findAllIds", query = "SELECT g.idGradiliste FROM Gradiliste g")
    , @NamedQuery(name = "Gradiliste.findByIdGradiliste", query = "SELECT g FROM Gradiliste g WHERE g.idGradiliste = :idGradiliste")
    , @NamedQuery(name = "Gradiliste.findByNaziv", query = "SELECT g FROM Gradiliste g WHERE g.naziv = :naziv")
    , @NamedQuery(name = "Gradiliste.findByDatumOsnivanja", query = "SELECT g FROM Gradiliste g WHERE g.datumOsnivanja = :datumOsnivanja")
    , @NamedQuery(name = "Gradiliste.findByBrojObjekata", query = "SELECT g FROM Gradiliste g WHERE g.brojObjekata = :brojObjekata")})
public class Gradiliste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idGradiliste")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idGradiliste;
    @Column(name = "Naziv")
    private String naziv;
    @Column(name = "DatumOsnivanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOsnivanja;
    @Column(name = "BrojObjekata")
    private Integer brojObjekata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGradiliste")
    private Collection<Magacin> magacinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGradiliste")
    private Collection<Objekat> objekatCollection;

    public Gradiliste() {
    }

    public Gradiliste(Integer idGradiliste) {
        this.idGradiliste = idGradiliste;
    }
    
    public Gradiliste(String naziv, Date datumOsnivanja) {
        this.naziv = naziv;
        this.datumOsnivanja = datumOsnivanja;
    }

    public Integer getIdGradiliste() {
        return idGradiliste;
    }

    public void setIdGradiliste(Integer idGradiliste) {
        this.idGradiliste = idGradiliste;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumOsnivanja() {
        return datumOsnivanja;
    }

    public void setDatumOsnivanja(Date datumOsnivanja) {
        this.datumOsnivanja = datumOsnivanja;
    }

    public Integer getBrojObjekata() {
        return brojObjekata;
    }

    public void setBrojObjekata(Integer brojObjekata) {
        this.brojObjekata = brojObjekata;
    }

    @XmlTransient
    public Collection<Magacin> getMagacinCollection() {
        return magacinCollection;
    }

    public void setMagacinCollection(Collection<Magacin> magacinCollection) {
        this.magacinCollection = magacinCollection;
    }

    @XmlTransient
    public Collection<Objekat> getObjekatCollection() {
        return objekatCollection;
    }

    public void setObjekatCollection(Collection<Objekat> objekatCollection) {
        this.objekatCollection = objekatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGradiliste != null ? idGradiliste.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gradiliste)) {
            return false;
        }
        Gradiliste other = (Gradiliste) object;
        if ((this.idGradiliste == null && other.idGradiliste != null) || (this.idGradiliste != null && !this.idGradiliste.equals(other.idGradiliste))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Gradiliste[ idGradiliste=" + idGradiliste + " ]";
    }
    
    public static List<Integer> findAllIds(EntityManager em) {
        TypedQuery<Integer> query = em.createNamedQuery("Gradiliste.findAllIds", Integer.class);
        List<Integer> results = query.getResultList();
        return results;
    }
    
    public static Gradiliste findById(EntityManager em, int idGradiliste) {
        TypedQuery<Gradiliste> query = em.createNamedQuery("Gradiliste.findByIdGradiliste", Gradiliste.class);
        query.setParameter("idGradiliste", idGradiliste);
        return query.getSingleResult();
    }
}
