package be.raphcho.databaselib;

import java.sql.SQLException;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private ArrayList<Class<?>>	tables	= new ArrayList<Class<?>>();
	
	public DatabaseHelper(Context context, String databaseName, int version) {
		super(context, databaseName, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		createTables();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
		deleteTables();
		createTables();
	}
	
	public void addTable(Class<? extends Model> tableClass) {
		if (!tables.contains(tableClass))
			tables.add(tableClass);
	}
	
	public void removeTable(Class<?> tableClass) {
		tables.remove(tableClass);
	}
	
	public void createTables() {
		for (Class<?> table : tables) {
			try {
				TableUtils.createTable(connectionSource, table);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteTables() {
		for (Class<?> table : tables) {
			try {
				TableUtils.dropTable(connectionSource, table, true);
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public <T> ArrayList<T> getAll(Class<T> classe) {
		try {
			return (ArrayList<T>) this.getDao(classe).queryForAll();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> ArrayList<T> getAll(Class<T> classe, String fieldToOrder, boolean asc) {
		try {
			return (ArrayList<T>) this.getDao(classe).queryBuilder().orderBy(fieldToOrder, asc).query();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> T findFirst(Class<T> classe) {
		ArrayList<T> liste = getAll(classe);
		if (liste != null && liste.size() > 0)
			return liste.get(0);
		else
			return null;
	}

	
	public <T> T findById(Class<T> classe, String id) {
		if (id != null) {
			ArrayList<T> temp = null;
			try {
				temp = (ArrayList<T>) getDao(classe).queryBuilder().where().eq("id", id).query();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			if (temp != null && temp.size() > 0)
				return temp.get(0);
		}
		return null;
	}
	
	public <T> T findById(Class<T> classe, String id, String orderField, boolean asc) {
		if (id != null) {
			ArrayList<T> temp;
			try {
				temp = (ArrayList<T>) getDao(classe).queryBuilder().orderBy(orderField, asc).where().eq("id", id).query();
				if (temp != null && temp.size() > 0)
					return temp.get(0);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public <T> ArrayList<T> findAllBy(Class<T> classe, String field, Object id) {
		if (id != null) {
			return findAllBy(classe, field, id, field, false);
		}
		else
			return null;
	}
	
	public <T> ArrayList<T> findAllBy(Class<T> classe, String field, Object id, String fieldToOrder, boolean asc) {
		if (id != null) {
			ArrayList<T> temp = null;
			try {
				temp = (ArrayList<T>) getDao(classe).queryBuilder().orderBy(fieldToOrder, asc).where().eq(field, id).query();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return temp;
		}
		else
			return null;
	}
	
	public <T> T getLastUpdatedObject(Class<T> classe) {
		try {
			ArrayList<T> temp = (ArrayList<T>) getDao(classe).queryBuilder().orderBy("updated_at", false).query();
			if (temp != null && temp.size() > 0)
				return (T) temp.get(0);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> String getLastUpatedString(Class<T> classe) {
		Model m = (Model) getLastUpdatedObject(classe);
		if (m != null && m.updated_at != null) {
			return m.updated_at;
		}
		return "0";
	}
	
	public <T extends Model> void deleteCollection(ArrayList<T> datas) {
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i) != null) {
					deleteItem(datas.get(i));
				}
			}
		}
	}
	
	public <T extends Model> void insertCollection(ArrayList<T> datas) {
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i) != null) {
					insertItem(datas.get(i));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
    public <T extends Model> void insertItem(T object) {
		try {
			Class<T> classe = (Class<T>) object.getClass();
			getDao(classe).createOrUpdate(object);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Model> void deleteItem(T object) {
		try {
			Class<T> classe = (Class<T>) object.getClass();
			DeleteBuilder db = getDao(classe).deleteBuilder();
			db.where().eq("id", ((Model) object).id);
			getDao(classe).delete(db.prepare());
			System.out.println("deleted");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
