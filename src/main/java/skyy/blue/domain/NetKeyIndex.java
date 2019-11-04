package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A NetKeyIndex.
 */
@Entity
@Table(name = "net_key_index")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NetKeyIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Column(name = "updated")
    private Boolean updated;

    @ManyToOne
    @JsonIgnoreProperties("netKeyIndices")
    private Node node;

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

    public NetKeyIndex index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean isUpdated() {
        return updated;
    }

    public NetKeyIndex updated(Boolean updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Node getNode() {
        return node;
    }

    public NetKeyIndex node(Node node) {
        this.node = node;
        return this;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetKeyIndex)) {
            return false;
        }
        return id != null && id.equals(((NetKeyIndex) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NetKeyIndex{" +
            "id=" + getId() +
            ", index=" + getIndex() +
            ", updated='" + isUpdated() + "'" +
            "}";
    }
}
