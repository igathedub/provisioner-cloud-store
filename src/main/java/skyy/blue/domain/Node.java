package skyy.blue.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Node.
 */
@Entity
@Table(name = "node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "unicast_address")
    private String unicastAddress;

    @Column(name = "config_complete")
    private Boolean configComplete;

    @Column(name = "default_ttl")
    private Integer defaultTTL;

    @Column(name = "cid")
    private String cid;

    @Column(name = "blacklisted")
    private Boolean blacklisted;

    @Column(name = "u_uid")
    private String uUID;

    @Column(name = "security")
    private String security;

    @Column(name = "crpl")
    private String crpl;

    @Column(name = "name")
    private String name;

    @Column(name = "device_key")
    private String deviceKey;

    @Column(name = "vid")
    private String vid;

    @Column(name = "pid")
    private String pid;

    @OneToOne
    @JoinColumn(unique = true)
    private Features features;

    @OneToMany(mappedBy = "node")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Element> elements = new HashSet<>();

    @OneToMany(mappedBy = "node")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KeyIndex> netKeys = new HashSet<>();

    @OneToMany(mappedBy = "node")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KeyIndex> appKeys = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("nodes")
    private NetworkConfiguration networkConfiguration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnicastAddress() {
        return unicastAddress;
    }

    public Node unicastAddress(String unicastAddress) {
        this.unicastAddress = unicastAddress;
        return this;
    }

    public void setUnicastAddress(String unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public Boolean isConfigComplete() {
        return configComplete;
    }

    public Node configComplete(Boolean configComplete) {
        this.configComplete = configComplete;
        return this;
    }

    public void setConfigComplete(Boolean configComplete) {
        this.configComplete = configComplete;
    }

    public Integer getDefaultTTL() {
        return defaultTTL;
    }

    public Node defaultTTL(Integer defaultTTL) {
        this.defaultTTL = defaultTTL;
        return this;
    }

    public void setDefaultTTL(Integer defaultTTL) {
        this.defaultTTL = defaultTTL;
    }

    public String getCid() {
        return cid;
    }

    public Node cid(String cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Boolean isBlacklisted() {
        return blacklisted;
    }

    public Node blacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
        return this;
    }

    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getuUID() {
        return uUID;
    }

    public Node uUID(String uUID) {
        this.uUID = uUID;
        return this;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public String getSecurity() {
        return security;
    }

    public Node security(String security) {
        this.security = security;
        return this;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getCrpl() {
        return crpl;
    }

    public Node crpl(String crpl) {
        this.crpl = crpl;
        return this;
    }

    public void setCrpl(String crpl) {
        this.crpl = crpl;
    }

    public String getName() {
        return name;
    }

    public Node name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public Node deviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
        return this;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getVid() {
        return vid;
    }

    public Node vid(String vid) {
        this.vid = vid;
        return this;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPid() {
        return pid;
    }

    public Node pid(String pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Features getFeatures() {
        return features;
    }

    public Node features(Features features) {
        this.features = features;
        return this;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public Set<Element> getElements() {
        return elements;
    }

    public Node elements(Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    public Node addElement(Element element) {
        this.elements.add(element);
        element.setNode(this);
        return this;
    }

    public Node removeElement(Element element) {
        this.elements.remove(element);
        element.setNode(null);
        return this;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }

    public Set<KeyIndex> getNetKeys() {
        return netKeys;
    }

    public Node netKeys(Set<KeyIndex> keyIndices) {
        this.netKeys = keyIndices;
        return this;
    }

    public Node addNetKey(KeyIndex keyIndex) {
        this.netKeys.add(keyIndex);
        keyIndex.setNode(this);
        return this;
    }

    public Node removeNetKey(KeyIndex keyIndex) {
        this.netKeys.remove(keyIndex);
        keyIndex.setNode(null);
        return this;
    }

    public void setNetKeys(Set<KeyIndex> keyIndices) {
        this.netKeys = keyIndices;
    }

    public Set<KeyIndex> getAppKeys() {
        return appKeys;
    }

    public Node appKeys(Set<KeyIndex> keyIndices) {
        this.appKeys = keyIndices;
        return this;
    }

    public Node addAppKey(KeyIndex keyIndex) {
        this.appKeys.add(keyIndex);
        keyIndex.setNode(this);
        return this;
    }

    public Node removeAppKey(KeyIndex keyIndex) {
        this.appKeys.remove(keyIndex);
        keyIndex.setNode(null);
        return this;
    }

    public void setAppKeys(Set<KeyIndex> keyIndices) {
        this.appKeys = keyIndices;
    }

    public NetworkConfiguration getNetworkConfiguration() {
        return networkConfiguration;
    }

    public Node networkConfiguration(NetworkConfiguration networkConfiguration) {
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
        if (!(o instanceof Node)) {
            return false;
        }
        return id != null && id.equals(((Node) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Node{" +
            "id=" + getId() +
            ", unicastAddress='" + getUnicastAddress() + "'" +
            ", configComplete='" + isConfigComplete() + "'" +
            ", defaultTTL=" + getDefaultTTL() +
            ", cid='" + getCid() + "'" +
            ", blacklisted='" + isBlacklisted() + "'" +
            ", uUID='" + getuUID() + "'" +
            ", security='" + getSecurity() + "'" +
            ", crpl='" + getCrpl() + "'" +
            ", name='" + getName() + "'" +
            ", deviceKey='" + getDeviceKey() + "'" +
            ", vid='" + getVid() + "'" +
            ", pid='" + getPid() + "'" +
            "}";
    }
}
