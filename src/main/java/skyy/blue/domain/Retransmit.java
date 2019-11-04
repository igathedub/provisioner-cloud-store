package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Retransmit.
 */
@Entity
@Table(name = "retransmit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Retransmit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "interval")
    private Integer interval;

    @OneToOne(mappedBy = "retransmit")
    @JsonIgnore
    private Publish publish;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public Retransmit count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getInterval() {
        return interval;
    }

    public Retransmit interval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Publish getPublish() {
        return publish;
    }

    public Retransmit publish(Publish publish) {
        this.publish = publish;
        return this;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Retransmit)) {
            return false;
        }
        return id != null && id.equals(((Retransmit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Retransmit{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", interval=" + getInterval() +
            "}";
    }
}
