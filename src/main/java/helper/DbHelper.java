package helper;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoURI;

import config.Config;
import constants.AppConstants;

public class DbHelper {

	public static final String MONGODB_URI;
	
	private static DB database = null;
	
	private static DbHelper instance = null;
	
	private DbHelper() {}
	
	public static DbHelper getInstance() {
		if (instance == null) {
			synchronized (MONGODB_URI) {
				if (instance == null) {
					instance = new DbHelper();
				}
			}
		}
		return instance;
	}
	
	
	static {
		if (Config.IS_DEBUG_BUILD) {
			MONGODB_URI = Config.MONGO_URI;
		} else {
			MONGODB_URI = System.getenv("MONGOHQ_URL");
		}
	}
	
	public void initDb() {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGODB_URI));
		try {
			database = mongoClient.getDB(AppConstants.DB_NAME);
		} catch (Exception e) {	}
	}
	
	public DB getDb() {
		if (database == null) {
			initDb();
		}
		
		return database;
	}
	
	public void closeDb() {
		if (database != null) {
			database.getMongo().close();
		}
	}
}
