package ke.fred.taskmanager;

/**
 * Created by Fredrick on 09/06/2015.
 */
public class Holder {
    String title, date, id, priority = null;

    String navItemTitle;
    int iconId;

    public void setId(String ID){
        this.id = ID;
    }

    public void setTitle(String Title){
        this.title = Title;
    }

    public void setDate(String Date){
        this.date = Date;
    }

    public void setPriority(String Priority){
        this.priority = Priority;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getPriority(){
        return priority;
    }

}
