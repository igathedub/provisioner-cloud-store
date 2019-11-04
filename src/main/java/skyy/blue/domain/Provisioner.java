package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Provisioner.
 */
@Entity
@Table(name = "provisioner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Provisioner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "u_uid")
    private String uUID;

    @Column(name = "provisioner_name")
    private String provisionerName;

    @OneToMany(mappedBy = "provisioner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllocatedGroupRange> aallocatedGroupRanges = new HashSet<>();

    @OneToMany(mappedBy = "provisioner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllocatedUnicastRange> aallocatedUnicastRanges = new HashSet<>();

    @OneToMany(mappedBy = "provisioner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllocatedSceneRange> aallocatedSceneRanges = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("provisioners")
    private NetworkConfiguration networkConfiguration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getuUID() {
        return uUID;
    }

    public Provisioner uUID(String uUID) {
        this.uUID = uUID;
        return this;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public String getProvisionerName() {
        return provisionerName;
    }

    public Provisioner provisionerName(String provisionerName) {
        this.provisionerName = provisionerName;
        return this;
    }

    public void setProvisionerName(String provisionerName) {
        this.provisionerName = provisionerName;
    }

    public Set<AllocatedGroupRange> getAallocatedGroupRanges() {
        return aallocatedGroupRanges;
    }

    public Provisioner aallocatedGroupRanges(Set<AllocatedGroupRange> allocatedGroupRanges) {
        this.aallocatedGroupRanges = allocatedGroupRanges;
        return this;
    }

    public Provisioner addAallocatedGroupRange(AllocatedGroupRange allocatedGroupRange) {
        this.aallocatedGroupRanges.add(allocatedGroupRange);
        allocatedGroupRange.setProvisioner(this);
        return this;
    }

    public Provisioner removeAallocatedGroupRange(AllocatedGroupRange allocatedGroupRange) {
        this.aallocatedGroupRanges.remove(allocatedGroupRange);
        allocatedGroupRange.setProvisioner(null);
        return this;
    }

    public void setAallocatedGroupRanges(Set<AllocatedGroupRange> allocatedGroupRanges) {
        this.aallocatedGroupRanges = allocatedGroupRanges;
    }

    public Set<AllocatedUnicastRange> getAallocatedUnicastRanges() {
        return aallocatedUnicastRanges;
    }

    public Provisioner aallocatedUnicastRanges(Set<AllocatedUnicastRange> allocatedUnicastRanges) {
        this.aallocatedUnicastRanges = allocatedUnicastRanges;
        return this;
    }

    public Provisioner addAallocatedUnicastRange(AllocatedUnicastRange allocatedUnicastRange) {
        this.aallocatedUnicastRanges.add(allocatedUnicastRange);
        allocatedUnicastRange.setProvisioner(this);
        return this;
    }

    public Provisioner removeAallocatedUnicastRange(AllocatedUnicastRange allocatedUnicastRange) {
        this.aallocatedUnicastRanges.remove(allocatedUnicastRange);
        allocatedUnicastRange.setProvisioner(null);
        return this;
    }

    public void setAallocatedUnicastRanges(Set<AllocatedUnicastRange> allocatedUnicastRanges) {
        this.aallocatedUnicastRanges = allocatedUnicastRanges;
    }

    public Set<AllocatedSceneRange> getAallocatedSceneRanges() {
        return aallocatedSceneRanges;
    }

    public Provisioner aallocatedSceneRanges(Set<AllocatedSceneRange> allocatedSceneRanges) {
        this.aallocatedSceneRanges = allocatedSceneRanges;
        return this;
    }

    public Provisioner addAallocatedSceneRange(AllocatedSceneRange allocatedSceneRange) {
        this.aallocatedSceneRanges.add(allocatedSceneRange);
        allocatedSceneRange.setProvisioner(this);
        return this;
    }

    public Provisioner removeAallocatedSceneRange(AllocatedSceneRange allocatedSceneRange) {
        this.aallocatedSceneRanges.remove(allocatedSceneRange);
        allocatedSceneRange.setProvisioner(null);
        return this;
    }

    public void setAallocatedSceneRanges(Set<AllocatedSceneRange> allocatedSceneRanges) {
        this.aallocatedSceneRanges = allocatedSceneRanges;
    }

    public NetworkConfiguration getNetworkConfiguration() {
        return networkConfiguration;
    }

    public Provisioner networkConfiguration(NetworkConfiguration networkConfiguration) {
        this.networkConfiguration = networkConfiguration;
        return this;
    }

    public void setNetworkConfiguration(NetworkConfiguration networkConfiguration) {
        this.networkConfiguration = networkConfiguration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provisioner)) {
            return false;
        }
        return id != null && id.equals(((Provisioner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Provisioner{" +
            "id=" + getId() +
            ", uUID='" + getuUID() + "'" +
            ", provisionerName='" + getProvisionerName() + "'" +
            "}";
    }
}
