package conference;


public class Participant {

    public Participant(String id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }
    public String id;
    public String displayName;

    public String toString(){
        return displayName + "(" + id + ")";
    }
}
