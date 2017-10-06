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
@Table(name = "Zaposleni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaposleni.findAllIds", query = "SELECT z.idZaposleni FROM Zaposleni z")
    , @NamedQuery(name = "Zaposleni.findByIdZaposleni", query = "SELECT z FROM Zaposleni z WHERE z.idZaposleni = :idZaposleni")
    , @NamedQuery(name = "Zaposleni.findByIme", query = "SELECT z FROM Zaposleni z WHERE z.ime = :ime")
    , @NamedQuery(name = "Zaposleni.findByPrezime", query = "SELECT z FROM Zaposleni z WHERE z.prezime = :prezime")
    , @NamedQuery(name = "Zaposleni.findByJmbg", query = "SELECT z FROM Zaposleni z WHERE z.jmbg = :jmbg")
    , @NamedQuery(name = "Zaposleni.findByPol", query = "SELECT z FROM Zaposleni z WHERE z.pol = :pol")
    , @NamedQuery(name = "Zaposleni.findByZiroRacun", query = "SELECT z FROM Zaposleni z WHERE z.ziroRacun = :ziroRacun")
    , @NamedQuery(name = "Zaposleni.findByEmail", query = "SELECT z FROM Zaposleni z WHERE z.email = :email")
    , @NamedQuery(name = "Zaposleni.findByBrTelefona", query = "SELECT z FROM Zaposleni z WHERE z.brTelefona = :brTelefona")
    , @NamedQuery(name = "Zaposleni.findByProsecnaOcena", query = "SELECT z FROM Zaposleni z WHERE z.prosecnaOcena = :prosecnaOcena")
    , @NamedQuery(name = "Zaposleni.findByBrZaduzeneRobe", query = "SELECT z FROM Zaposleni z WHERE z.brZaduzeneRobe = :brZaduzeneRobe")
    , @NamedQuery(name = "Zaposleni.findByUkupnoIsplacenIznos", query = "SELECT z FROM Zaposleni z WHERE z.ukupnoIsplacenIznos = :ukupnoIsplacenIznos")})
public class Zaposleni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idZaposleni")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idZaposleni;
    @Column(name = "Ime")
    private String ime;
    @Column(name = "Prezime")
    private String prezime;
    @Column(name = "JMBG")
    private String jmbg;
    @Column(name = "Pol")
    private Character pol;
    @Column(name = "ZiroRacun")
    private String ziroRacun;
    @Column(name = "Email")
    private String email;
    @Column(name = "BrTelefona")
    private String brTelefona;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ProsecnaOcena")
    private BigDecimal prosecnaOcena;
    @Column(name = "BrZaduzeneRobe")
    private Integer brZaduzeneRobe;
    @Column(name = "UkupnoIsplacenIznos")
    private BigDecimal ukupnoIsplacenIznos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSef")
    private Collection<Magacin> magacinCollection;
    @JoinColumn(name = "idMagacin", referencedColumnName = "idMagacin")
    @ManyToOne
    private Magacin idMagacin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idZaposleni")
    private Collection<Radi> radiCollection;
    @OneToMany(mappedBy = "idZaposleni")
    private Collection<Zaduzenje> zaduzenjeCollection;

    public Zaposleni() {
    }

    public Zaposleni(Integer idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    public Zaposleni(String ime, String prezime, String jmbg, Character pol, String ziroRacun, String email, String brTelefona) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.pol = pol;
        this.ziroRacun = ziroRacun;
        this.email = email;
        this.brTelefona = brTelefona;
    }

    public Integer getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(Integer idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Character getPol() {
        return pol;
    }

    public void setPol(Character pol) {
        this.pol = pol;
    }

    public String getZiroRacun() {
        return ziroRacun;
    }

    public void setZiroRacun(String ziroRacun) {
        this.ziroRacun = ziroRacun;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrTelefona() {
        return brTelefona;
    }

    public void setBrTelefona(String brTelefona) {
        this.brTelefona = brTelefona;
    }

    public BigDecimal getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(BigDecimal prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Integer getBrZaduzeneRobe() {
        return brZaduzeneRobe;
    }

    public void setBrZaduzeneRobe(Integer brZaduzeneRobe) {
        this.brZaduzeneRobe = brZaduzeneRobe;
    }

    public BigDecimal getUkupnoIsplacenIznos() {
        return ukupnoIsplacenIznos;
    }

    public void setUkupnoIsplacenIznos(BigDecimal ukupnoIsplacenIznos) {
        this.ukupnoIsplacenIznos = ukupnoIsplacenIznos;
    }

    @XmlTransient
    public Collection<Magacin> getMagacinCollection() {
        return magacinCollection;
    }

    public void setMagacinCollection(Collection<Magacin> magacinCollection) {
        this.magacinCollection = magacinCollection;
    }

    public Magacin getIdMagacin() {
        return idMagacin;
    }

    public void setIdMagacin(Magacin idMagacin) {
        this.idMagacin = idMagacin;
    }

    @XmlTransient
    public Collection<Radi> getRadiCollection() {
        return radiCollection;
    }

    public void setRadiCollection(Collection<Radi> radiCollection) {
        this.radiCollection = radiCollection;
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
        hash += (idZaposleni != null ? idZaposleni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zaposleni)) {
            return false;
        }
        Zaposleni other = (Zaposleni) object;
        if ((this.idZaposleni == null && other.idZaposleni != null) || (this.idZaposleni != null && !this.idZaposleni.equals(other.idZaposleni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Zaposleni[ idZaposleni=" + idZaposleni + " ]";
    }
    
    public static Zaposleni findById(EntityManager em, int idZaposleni) {
        TypedQuery<Zaposleni> query = em.createNamedQuery("Zaposleni.findByIdZaposleni", Zaposleni.class);
        query.setParameter("idZaposleni", idZaposleni);
        return query.getSingleResult();
    }
    
    public static List<Integer> findAllIds(EntityManager em) {
        TypedQuery<Integer> query = em.createNamedQuery("Zaposleni.findAllIds", Integer.class);
        List<Integer> results = query.getResultList();
        return results;
    }
    
}
