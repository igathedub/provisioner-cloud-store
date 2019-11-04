package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A NetKey.
 */
@Entity
@Table(name = "net_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NetKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phase")
    private Integer phase;

    @Column(name = "min_security")
    private String minSecurity;

    @Column(name = "key")
    private String key;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "name")
    private String name;

    @Column(name = "index")
    private Integer index;

    @ManyToOne
    @JsonIgnoreProperties("netKeys")
    private NetworkConfiguration networkConfiguration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhase() {
        return phase;
    }

    public NetKey phase(Integer phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getMinSecurity() {
        return minSecurity;
    }

    public NetKey minSecurity(String minSecurity) {
        this.minSecurity = minSecurity;
        return this;
    }

    public void setMinSecurity(String minSecurity) {
        this.minSecurity = minSecurity;
    }

    public String getKey() {
        return key;
    }

    public NetKey key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public NetKey timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public NetKey name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public NetKey index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public NetworkConfiguration getNetworkConfiguration() {
        return networkConfiguration;
    }

    public NetKey networkConfiguration(NetworkConfiguration networkConfiguration) {
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
        if (!(o instanceof NetKey)) {
            return false;
        }
        return id != null && id.equals(((NetKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NetKey{" +
            "id=" + getId() +
            ", phase=" + getPhase() +
            ", minSecurity='" + getMinSecurity() + "'" +
            ", key='" + getKey() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", name='" + getName() + "'" +
            ", index=" + getIndex() +
            "}";
    }
}
