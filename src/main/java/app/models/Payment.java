package app.models;

import com.skillcorp.sejoframework.database.Model;

public class Payment extends Model {

	public int id;
	public int user_id;
	public String description;
	public int status;
	public double amount;
	public boolean timestamps = true;
	//private Bill bill = null;
	private User user = null;
	
	public Payment()
	{
		this.pkey = "id";
		this.table = "payments";
	}
	
	/*public Bill bill()
	{
		if(bill==null){
			this.bill = (Bill) this.hasOne(Bill.class,"payment_id");
		}
		return this.bill;
	}*/
	
	public User user()
	{
		if(this.user==null){
			this.user = (User) this.belongsTo(User.class,"user_id");
		}
		return this.user;
	}
}
