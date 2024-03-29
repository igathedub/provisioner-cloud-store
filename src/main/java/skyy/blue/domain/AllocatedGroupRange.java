package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AllocatedGroupRange.
 */
@Entity
@Table(name = "allocated_group_range")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllocatedGroupRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "low_address")
    private String lowAddress;

    @Column(name = "high_address")
    private String highAddress;

    @ManyToOne
    @JsonIgnoreProperties("allocatedGroupRanges")
    private Provisioner provisioner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLowAddress() {
        return lowAddress;
    }

    public AllocatedGroupRange lowAddress(String lowAddress) {
        this.lowAddress = lowAddress;
        return this;
    }

    public void setLowAddress(String lowAddress) {
        this.lowAddress = lowAddress;
    }

    public String getHighAddress() {
        return highAddress;
    }

    public AllocatedGroupRange highAddress(String highAddress) {
        this.highAddress = highAddress;
        return this;
    }

    public void setHighAddress(String highAddress) {
        this.highAddress = highAddress;
    }

    public Provisioner getProvisioner() {
        return provisioner;
    }

    public AllocatedGroupRange provisioner(Provisioner provisioner) {
        this.provisioner = provisioner;
        return this;
    }

    public void setProvisioner(Provisioner provisioner) {
        this.provisioner = provisioner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AllocatedGroupRange)) {
            return false;
        }
        return id != null && id.equals(((AllocatedGroupRange) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AllocatedGroupRange{" +
            "id=" + getId() +
            ", lowAddress='" + getLowAddress() + "'" +
            ", highAddress='" + getHighAddress() + "'" +
            "}";
    }
}
