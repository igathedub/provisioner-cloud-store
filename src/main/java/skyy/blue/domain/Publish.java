package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Publish.
 */
@Entity
@Table(name = "publish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Column(name = "credentials")
    private Integer credentials;

    @Column(name = "ttl")
    private Integer ttl;

    @Column(name = "period")
    private Integer period;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private Retransmit retransmit;

    @OneToOne(mappedBy = "publish")
    @JsonIgnore
    private Model model;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public Publish index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCredentials() {
        return credentials;
    }

    public Publish credentials(Integer credentials) {
        this.credentials = credentials;
        return this;
    }

    public void setCredentials(Integer credentials) {
        this.credentials = credentials;
    }

    public Integer getTtl() {
        return ttl;
    }

    public Publish ttl(Integer ttl) {
        this.ttl = ttl;
        return this;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Integer getPeriod() {
        return period;
    }

    public Publish period(Integer period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getAddress() {
        return address;
    }

    public Publish address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Retransmit getRetransmit() {
        return retransmit;
    }

    public Publish retransmit(Retransmit retransmit) {
        this.retransmit = retransmit;
        return this;
    }

    public void setRetransmit(Retransmit retransmit) {
        this.retransmit = retransmit;
    }

    public Model getModel() {
        return model;
    }

    public Publish model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publish)) {
            return false;
        }
        return id != null && id.equals(((Publish) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Publish{" +
            "id=" + getId() +
            ", index=" + getIndex() +
            ", credentials=" + getCredentials() +
            ", ttl=" + getTtl() +
            ", period=" + getPeriod() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
