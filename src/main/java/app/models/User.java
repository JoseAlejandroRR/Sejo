package app.models;

import com.skillcorp.sejoframework.database.Model;

import java.util.ArrayList;
import java.util.Date;

public class User extends Model {

    public int id;
    private String name;
    public String lastname;
    public String document;
    public Date birthday;
    public int status;
    public boolean timestamps = true;
    private ArrayList payments = null;
    //protected boolean algo;

    public User()
    {
        this.pkey = "id";
        this.table = "users";
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String _name)
    {
        this.name = _name;
    }

    public ArrayList payments()
    {
        if(this.payments==null) this.payments = this.hasMany(Payment.class,"user_id");
        return this.payments;
    }

}
