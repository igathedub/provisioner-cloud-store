package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Element.
 */
@Entity
@Table(name = "element")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Element implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Column(name = "location")
    private String location;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "element")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Model> models = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("elements")
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

    public Element index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getLocation() {
        return location;
    }

    public Element location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Element name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Model> getModels() {
        return models;
    }

    public Element models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public Element addModel(Model model) {
        this.models.add(model);
        model.setElement(this);
        return this;
    }

    public Element removeModel(Model model) {
        this.models.remove(model);
        model.setElement(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public Node getNode() {
        return node;
    }

    public Element node(Node node) {
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
        if (!(o instanceof Element)) {
            return false;
        }
        return id != null && id.equals(((Element) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Element{" +
            "id=" + getId() +
            ", index=" + getIndex() +
            ", location='" + getLocation() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
