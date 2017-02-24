package twice.pbdtest;

/**
 * Created by Alif on 20/02/2017.
 */

public class User {
    private int fireGem;
    private int iceGem;
    private String name;
    private String token;

    public User(){
        this.name = "";
        this.fireGem = 0;
        this.iceGem = 0;
    }

    public User(String _name){
        this.name = _name;
        this.token = "";
        this.fireGem = 0;
        this.iceGem = 0;
    }

    public User(String _name, String _token){
        this.name = _name;
        this.token = _token;
        this.fireGem = 0;
        this.iceGem = 0;
    }

    public int getFireGem(){
        return fireGem;
    }

    public int getIceGem(){
        return iceGem;
    }

    public String getName(){
        return name;
    }

    public String getToken(){
        return token;
    }

    public void setFireGem(int temp){
        fireGem = temp;
    }

    public void setIceGem(int temp){
        iceGem = temp;
    }

    public void setName(String temp){
        name = temp;
    }

    public void setToken(String temp){
        token = temp;
    }
}
