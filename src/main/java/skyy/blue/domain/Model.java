package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Model.
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "model_id")
    private String modelId;

    @Column(name = "subscribe")
    private String subscribe;

    @Column(name = "bind")
    private String bind;

    @OneToOne
    @JoinColumn(unique = true)
    private Publish publish;

    @ManyToOne
    @JsonIgnoreProperties("models")
    private Element element;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public Model modelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public Model subscribe(String subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getBind() {
        return bind;
    }

    public Model bind(String bind) {
        this.bind = bind;
        return this;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public Publish getPublish() {
        return publish;
    }

    public Model publish(Publish publish) {
        this.publish = publish;
        return this;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }

    public Element getElement() {
        return element;
    }

    public Model element(Element element) {
        this.element = element;
        return this;
    }

    public void setElement(Element element) {
        this.element = element;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model)) {
            return false;
        }
        return id != null && id.equals(((Model) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", modelId='" + getModelId() + "'" +
            ", subscribe='" + getSubscribe() + "'" +
            ", bind='" + getBind() + "'" +
            "}";
    }
}
