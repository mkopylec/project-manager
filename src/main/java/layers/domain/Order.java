package layers.domain;

/**
 * Entity
 */
public class Order {

    private String identifier;
    private boolean fulfilled;

    Order(String identifier, boolean fulfilled) {
        this.identifier = identifier;
        this.fulfilled = fulfilled;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void fulfill() {
        fulfilled = true;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }
}
