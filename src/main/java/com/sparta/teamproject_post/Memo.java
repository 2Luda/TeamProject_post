import java.time.LocalDateTime;

public class Memo {
    private int id;
    private String name;
    private String password;
    private String content;
    private String data;

    public Memo(int id, String name, String password, String content, String data) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.content = content;
        this.data = data;
    }

    public Memo(int id, String name) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getContent() {
        return content;
    }

    public String getData() {
        return data;
    }
    public void setNumber(int number){
        this.id =id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setData(){
        this.data = data;
    }
    public String toString(){
        return name + id;
    }
}

