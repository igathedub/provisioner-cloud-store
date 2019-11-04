package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NetworkConfiguration.
 */
@Entity
@Table(name = "network_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NetworkConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "mesh_uuid")
    private String meshUUID;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "mesh_name")
    private Integer meshName;

    @ManyToOne
    @JsonIgnoreProperties("networkConfigurations")
    private Facility facility;

    @OneToMany(mappedBy = "networkConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Provisioner> provisioners = new HashSet<>();

    @OneToMany(mappedBy = "networkConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Node> nodes = new HashSet<>();

    @OneToMany(mappedBy = "networkConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MeshGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "networkConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NetKey> netKeys = new HashSet<>();

    @OneToMany(mappedBy = "networkConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AppKey> appKeys = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeshUUID() {
        return meshUUID;
    }

    public NetworkConfiguration meshUUID(String meshUUID) {
        this.meshUUID = meshUUID;
        return this;
    }

    public void setMeshUUID(String meshUUID) {
        this.meshUUID = meshUUID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public NetworkConfiguration timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMeshName() {
        return meshName;
    }

    public NetworkConfiguration meshName(Integer meshName) {
        this.meshName = meshName;
        return this;
    }

    public void setMeshName(Integer meshName) {
        this.meshName = meshName;
    }

    public Facility getFacility() {
        return facility;
    }

    public NetworkConfiguration facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<Provisioner> getProvisioners() {
        return provisioners;
    }

    public NetworkConfiguration provisioners(Set<Provisioner> provisioners) {
        this.provisioners = provisioners;
        return this;
    }

    public NetworkConfiguration addProvisioner(Provisioner provisioner) {
        this.provisioners.add(provisioner);
        provisioner.setNetworkConfiguration(this);
        return this;
    }

    public NetworkConfiguration removeProvisioner(Provisioner provisioner) {
        this.provisioners.remove(provisioner);
        provisioner.setNetworkConfiguration(null);
        return this;
    }

    public void setProvisioners(Set<Provisioner> provisioners) {
        this.provisioners = provisioners;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public NetworkConfiguration nodes(Set<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    public NetworkConfiguration addNode(Node node) {
        this.nodes.add(node);
        node.setNetworkConfiguration(this);
        return this;
    }

    public NetworkConfiguration removeNode(Node node) {
        this.nodes.remove(node);
        node.setNetworkConfiguration(null);
        return this;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Set<MeshGroup> getGroups() {
        return groups;
    }

    public NetworkConfiguration groups(Set<MeshGroup> meshGroups) {
        this.groups = meshGroups;
        return this;
    }

    public NetworkConfiguration addGroup(MeshGroup meshGroup) {
        this.groups.add(meshGroup);
        meshGroup.setNetworkConfiguration(this);
        return this;
    }

    public NetworkConfiguration removeGroup(MeshGroup meshGroup) {
        this.groups.remove(meshGroup);
        meshGroup.setNetworkConfiguration(null);
        return this;
    }

    public void setGroups(Set<MeshGroup> meshGroups) {
        this.groups = meshGroups;
    }

    public Set<NetKey> getNetKeys() {
        return netKeys;
    }

    public NetworkConfiguration netKeys(Set<NetKey> netKeys) {
        this.netKeys = netKeys;
        return this;
    }

    public NetworkConfiguration addNetKey(NetKey netKey) {
        this.netKeys.add(netKey);
        netKey.setNetworkConfiguration(this);
        return this;
    }

    public NetworkConfiguration removeNetKey(NetKey netKey) {
        this.netKeys.remove(netKey);
        netKey.setNetworkConfiguration(null);
        return this;
    }

    public void setNetKeys(Set<NetKey> netKeys) {
        this.netKeys = netKeys;
    }

    public Set<AppKey> getAppKeys() {
        return appKeys;
    }

    public NetworkConfiguration appKeys(Set<AppKey> appKeys) {
        this.appKeys = appKeys;
        return this;
    }

    public NetworkConfiguration addAppKey(AppKey appKey) {
        this.appKeys.add(appKey);
        appKey.setNetworkConfiguration(this);
        return this;
    }

    public NetworkConfiguration removeAppKey(AppKey appKey) {
        this.appKeys.remove(appKey);
        appKey.setNetworkConfiguration(null);
        return this;
    }

    public void setAppKeys(Set<AppKey> appKeys) {
        this.appKeys = appKeys;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkConfiguration)) {
            return false;
        }
        return id != null && id.equals(((NetworkConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NetworkConfiguration{" +
            "id=" + getId() +
            ", meshUUID='" + getMeshUUID() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", meshName=" + getMeshName() +
            "}";
    }
}
