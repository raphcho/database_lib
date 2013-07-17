package be.raphcho.databaselib;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Model implements Serializable {
	
	private static final long	serialVersionUID	= 5018131877349755987L;
	@DatabaseField(id = true)
	public String		      id;
	
	@DatabaseField
	public String		      deleted_at;
	
	@DatabaseField
	public String		      updated_at;
	
	@DatabaseField
	public String		      created_at;
}