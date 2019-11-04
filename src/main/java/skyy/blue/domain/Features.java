package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Features.
 */
@Entity
@Table(name = "features")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Features implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "proxy")
    private Integer proxy;

    @Column(name = "friend")
    private Integer friend;

    @Column(name = "relay")
    private Integer relay;

    @Column(name = "low_power")
    private Integer lowPower;

    @OneToOne(mappedBy = "features")
    @JsonIgnore
    private Node node;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProxy() {
        return proxy;
    }

    public Features proxy(Integer proxy) {
        this.proxy = proxy;
        return this;
    }

    public void setProxy(Integer proxy) {
        this.proxy = proxy;
    }

    public Integer getFriend() {
        return friend;
    }

    public Features friend(Integer friend) {
        this.friend = friend;
        return this;
    }

    public void setFriend(Integer friend) {
        this.friend = friend;
    }

    public Integer getRelay() {
        return relay;
    }

    public Features relay(Integer relay) {
        this.relay = relay;
        return this;
    }

    public void setRelay(Integer relay) {
        this.relay = relay;
    }

    public Integer getLowPower() {
        return lowPower;
    }

    public Features lowPower(Integer lowPower) {
        this.lowPower = lowPower;
        return this;
    }

    public void setLowPower(Integer lowPower) {
        this.lowPower = lowPower;
    }

    public Node getNode() {
        return node;
    }

    public Features node(Node node) {
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
        if (!(o instanceof Features)) {
            return false;
        }
        return id != null && id.equals(((Features) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Features{" +
            "id=" + getId() +
            ", proxy=" + getProxy() +
            ", friend=" + getFriend() +
            ", relay=" + getRelay() +
            ", lowPower=" + getLowPower() +
            "}";
    }
}
