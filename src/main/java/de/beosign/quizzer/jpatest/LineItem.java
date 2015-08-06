package de.beosign.quizzer.jpatest;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(LineItemKey.class)
public class LineItem {
    private Order order;
    private int lineNr;

    public LineItem() {
    }

    public LineItem(Order order, int lineNr) {
        this.order = order;
        this.lineNr = lineNr;
    }

    @Id
    @ManyToOne
    // Must us JoinColumn to tell JPA which column this property maps to in the ORDER table
    @JoinColumn(name = "id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Id
    public int getLineNr() {
        return lineNr;
    }

    public void setLineNr(int lineNr) {
        this.lineNr = lineNr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + lineNr;
        result = prime * result + ((order == null) ? 0 : order.hashCode());
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
        if (!(obj instanceof LineItem)) {
            return false;
        }
        LineItem other = (LineItem) obj;
        if (lineNr != other.lineNr) {
            return false;
        }
        if (order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!order.equals(other.order)) {
            return false;
        }
        return true;
    }

}
