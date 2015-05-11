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


    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted(){
        return false;
    }

    public void setIsDeleted( Boolean deleted){

    }
}
