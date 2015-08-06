package de.beosign.quizzer.jpatest;

import java.io.Serializable;

public class LineItemKey implements Serializable {
    private static final long serialVersionUID = 1812145319571292138L;

    private int lineNr;

    // Name this property exactly as the one in LineItem!
    // This must be the primitive type of the id field, not the java entity!
    private Long order;

    public LineItemKey() {
    }

    public LineItemKey(int lineNr, Long order) {
        super();
        this.lineNr = lineNr;
        this.order = order;
    }

    public int getLineNr() {
        return lineNr;
    }

    public void setLineNr(int lineNr) {
        this.lineNr = lineNr;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + lineNr;
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
        if (!(obj instanceof LineItemKey)) {
            return false;
        }
        LineItemKey other = (LineItemKey) obj;
        if (order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!order.equals(other.order)) {
            return false;
        }
        if (lineNr != other.lineNr) {
            return false;
        }
        return true;
    }

}
