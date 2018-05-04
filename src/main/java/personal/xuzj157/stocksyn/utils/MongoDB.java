/**
 * @Author Teddy
 */
package personal.xuzj157.stocksyn.utils;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import java.util.*;
import java.util.regex.Pattern;

//todo 创建索引
public class MongoDB {

	private static MongoDatabase db;

	public static synchronized MongoDatabase getDB(){
		if(db==null){

			MongoClient client=new MongoClient("127.0.0.1", Integer.parseInt("27017"));
			db=client.getDatabase("stock");
		}
		return db;
	}

	public static MongoCollection<DBObject> getCollection(String collectionName){
		return getDB().getCollection(collectionName, DBObject.class);
	}


	public static void update(MongoCollection<DBObject> collection, String idColumn, DBObject obj, boolean upsert){
		List<String> columns=new ArrayList<>();
		columns.add(idColumn);
		update(collection,columns,obj,upsert);
	}

	public static void update(MongoCollection<DBObject> collection, List<String> queryColumns, DBObject obj, boolean upsert){
		BasicDBObject find=new BasicDBObject();
		for(String column:queryColumns)
			find.put(column, obj.get(column));
		BasicDBObject update=new BasicDBObject();
		DBObject setObj=new BasicDBObject();
		for(String key:obj.keySet()){
			if(find.get(key)==null){
				if("_id".equals(key)){
					setObj.put("id", obj.get(key));
				}
				else
					setObj.put(key, obj.get(key));
			}

		}
		update.put("$set", setObj);
		UpdateOptions options=new UpdateOptions();
		options.upsert(upsert);
		collection.updateOne(find, update, options);
	}

	/**
	 * 插入操作
	 * @param collectionName
	 * @param result
	 */
	public static void writeResultObjectToDB(String collectionName, Object result) {
		BasicDBObject obj = (BasicDBObject) com.mongodb.util.JSON
				.parse(JSON.toJSONString(result));
		MongoDB.getCollection(collectionName).insertOne(obj);
	}

	public static void writeResultObjectToDB(String collectionName, Object result, String idColumn) {
		BasicDBObject obj = (BasicDBObject) com.mongodb.util.JSON
				.parse(JSON.toJSONString(result));
		MongoDB.update(MongoDB.getCollection(collectionName), idColumn,
				obj, true);
	}

	public static void writeResultObjectToDB(String collectionName, Object result,List<String> idColumns) {
		BasicDBObject obj = (BasicDBObject) com.mongodb.util.JSON
				.parse(JSON.toJSONString(result));
		MongoDB.update(MongoDB.getCollection(collectionName), idColumns,
				obj, true);
	}
	public static void writeResultListToDB(String collectionName,List<?> result,String idColumn){
		for(Object item:result){
			writeResultObjectToDB(collectionName, item,idColumn);
		}
	}

	public static void writeResultListToDB(String collectionName,List<?> result){
		for(Object item:result){
			writeResultObjectToDB(collectionName, item);
		}
	}

	public  static <TResult> List<TResult> getResultListFromDB(String collectionName, BasicDBObject filter, BasicDBObject sort, int limit, Class<TResult> resultClass){
		return getResultListFromDB(collectionName, filter, sort, limit, -1, resultClass);
	}

	public  static <TResult> List<TResult> getResultListFromDB(String collectionName, BasicDBObject filter, BasicDBObject sort, int limit, int skip, Class<TResult> resultClass){
		MongoCollection<DBObject> collection=getCollection(collectionName);
		FindIterable<DBObject> result=collection.find(filter).sort(sort);
		if(limit>0)
			result=result.limit(limit);
		if(skip>0)
			result=result.skip(skip);
		List<TResult> list=new ArrayList<>();
		for(DBObject t:result){
			list.add(JSON.parseObject(com.mongodb.util.JSON.serialize(t),resultClass));
		}
		return list;
	}

	public  static <TResult> List<TResult> getResultListFromDB(String collectionName, BasicDBObject filter, BasicDBObject sort, Class<TResult> resultClass){
		return getResultListFromDB(collectionName, filter, sort,-1,-1,resultClass);
	}

	public  static <TResult> List<TResult> getResultListFromDB(String collectionName, BasicDBObject filter, Class<TResult> resultClass){
		return getResultListFromDB(collectionName, filter, null,-1,-1,resultClass);
	}

	public static BasicDBObject getLikeStr(String findStr) {
		Pattern pattern = Pattern.compile("^.*" + findStr + ".*$", Pattern.CASE_INSENSITIVE);
		return new BasicDBObject("$regex", pattern);
	}

	//endWith文件扩展名
	public static BasicDBObject getEndWithStr(String findStr) {
		Pattern pattern = Pattern.compile(findStr + "$", Pattern.CASE_INSENSITIVE);
		return new BasicDBObject("$regex", pattern);
	}

	public static <T> T dbObjectToClass(DBObject o, Class<T> resultClass){
		return JSON.parseObject(com.mongodb.util.JSON.serialize(o),resultClass);
	}


}