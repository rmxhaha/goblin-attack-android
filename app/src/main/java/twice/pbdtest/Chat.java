package twice.pbdtest;

/**
 * Created by Alif on 25/02/2017.
 */

public class Chat {
    public String body;
    public int flag;

    public Chat(){
        this.body = "";
        this.flag = 0;
    }

    public Chat(String _body, int _flag){
        this.body = _body;
        this.flag = _flag;
    }
}
