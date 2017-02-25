package twice.pbdtest.CollectGem;

/**
 * Created by rmxhaha on 2/26/2017.
 */

public class Gem {
    private int count;
    private String type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Gem(int count, String type) {

        this.count = count;
        this.type = type;
    }
}
