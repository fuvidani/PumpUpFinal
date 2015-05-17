package sepm.ss15.grp16.entity;

/**
 * Created by lukas on 11.05.2015.
 */
public abstract class AbsractCategory implements DTO{

    protected Integer id;
    protected String name;

    public AbsractCategory(){

    }


    public AbsractCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted(){
        return false;
    }

    public void setIsDeleted( Boolean deleted){

    }

    @Override
    public String toString() {
        return "AbsractCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbsractCategory that = (AbsractCategory) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
