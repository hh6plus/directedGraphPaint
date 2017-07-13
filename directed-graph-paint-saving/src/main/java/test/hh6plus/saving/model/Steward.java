package test.hh6plus.saving.model;

/**
 * Created by huhong02 on 17/7/11.
 */
public class Steward {
    private String name;
    private String misId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(String misId) {
        this.misId = misId;
    }

    public Steward() {
    }

    public Steward(String name, String misId) {
        this.name = name;
        this.misId = misId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Steward steward = (Steward) o;

        if (name != null ? !name.equals(steward.name) : steward.name != null) return false;
        return !(misId != null ? !misId.equals(steward.misId) : steward.misId != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (misId != null ? misId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Steward{" +
                "name='" + name + '\'' +
                ", misId='" + misId + '\'' +
                '}';
    }
}
