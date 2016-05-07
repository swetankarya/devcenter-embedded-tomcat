package helper;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import constants.AppConstants;

public class CommunicationManager {

	public static List<DBObject> getNearByWifi(String locality, String subAdminArea, String countryName) {
		List<DBObject> dbObjects = new ArrayList<DBObject>();
		DBCollection dbCollection = DbHelper.getInstance().getDb().getCollectionFromString(AppConstants.TABLE_WIFI_MODEL);
		if (dbCollection != null && countryName != null && !countryName.trim().isEmpty()) {
			List<BasicDBObject> queryList = new ArrayList<BasicDBObject>();
			List<BasicDBObject> query = new ArrayList<BasicDBObject>();
			if (locality != null && !locality.trim().isEmpty()) {
				query.add(new BasicDBObject(AppConstants.LOCALITY_NAME, locality));
				query.add(new BasicDBObject(AppConstants.COUNTRY_NAME, countryName));
				
				queryList.add(new BasicDBObject("$and", query));
			}
			
			query = new ArrayList<BasicDBObject>();
			if (subAdminArea != null && !subAdminArea.trim().isEmpty()) {
				query.add(new BasicDBObject(AppConstants.SUB_ADMIN_AREA, subAdminArea));
				query.add(new BasicDBObject(AppConstants.COUNTRY_NAME, countryName));
				
				queryList.add(new BasicDBObject("$and", query));
			}
			
			DBCursor dbCursor = dbCollection.find(new BasicDBObject("$or", queryList));
			
			while (dbCursor.hasNext()) {
				dbObjects.add((DBObject) dbCursor.next());
			}
		}
		return dbObjects;
	}
}
