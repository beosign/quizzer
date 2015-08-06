package de.beosign.quizzer.jpatest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "\"Order\"")
public class Order {
    public enum OrderType {
        DEFAULT, PRIME, VIP
    }

    private Long id;
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private OrderType orderType = OrderType.DEFAULT;
    private List<LineItem> lineItems = new ArrayList<LineItem>();

    public Order() {
    }

    public Order(Long id, List<LineItem> lineItems) {
        this.id = id;
        this.lineItems = lineItems;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @PrePersist
    private void prePersist() {
        if (orderDate == null) {
            orderDate = new Date(System.currentTimeMillis());
        }
    }

    // TODO: equals and hascode should not be used on generated ids!
    // See: http://stackoverflow.com/questions/7340824/should-the-id-field-of-a-jpa-entity-be-considered-in-equals-and-hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Order)) {
            return false;
        }
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

}
