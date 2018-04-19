import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 *<p>项目名称:TheService</p>
 *<p>文件名称:MongoService.java</p>
 *<p>文件描述:</p>
 *<p>内容摘要:简要描述本文件的内容，包括主要模块、函数及能的说明</p>
 *<p>其他说明:其它内容的说明</p>
 *<p>创建日期:2016-4-7 下午2:24:31</p>
 *<p>创建用户： wangzp</p>
 */
public class MongoTest {
    public static final int DEFAULT_LIMIT = 50;
    public static final int UNLIMIT = -1;

    private static DB db;
    private static MongoClient mongoClient;
    private static String MULDB_MONGO_URL = null;
    private static int MULDB_MONGO_PORT = 27017;
    private static int MULDB_MONGO_POOLSIZE = 500;
    private static int MULDB_MONGO_BLOCKSIZE = 10;
    private static String MULDB_MONGO_NAME = null;
    private static String MULDB_MONGO_USER = null;
    private static String MULDB_MONGO_PASSWORD = null;

    private static void initOption(){
        try {
            MULDB_MONGO_URL = "10.1.48.180";
            MULDB_MONGO_PORT = 27017;
            MULDB_MONGO_NAME = "dshamc";
            MULDB_MONGO_USER = "";
            MULDB_MONGO_PASSWORD= "";
        } catch (Exception e) {
            throw new RuntimeException("mongo配置参数出错", e);
        }
    }

    private static void init() {
        try {
            initOption();

            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress(MULDB_MONGO_URL, MULDB_MONGO_PORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential(MULDB_MONGO_USER, MULDB_MONGO_NAME, MULDB_MONGO_PASSWORD.toCharArray());

            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            List<MongoClientOptions> options = new ArrayList<MongoClientOptions>();
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(MULDB_MONGO_POOLSIZE);
            builder.threadsAllowedToBlockForConnectionMultiplier(MULDB_MONGO_BLOCKSIZE);
            builder.maxWaitTime(10000);
            builder.socketTimeout(2 * 60 * 1000);// 设置超时时间2分钟
            builder.socketKeepAlive(true);
            MongoClientOptions option = builder.build();
            options.add(option);
            //通过连接认证获取MongoDB连接
//            mongoClient = new MongoClient(addrs, credentials, option);
            mongoClient = new MongoClient( MULDB_MONGO_URL , 27017 );
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @SuppressWarnings("deprecation")
    public static DB getDB() {
        if(db == null){
            init();
            db = mongoClient.getDB(MULDB_MONGO_NAME);
        }
        return db;
    }

    /**
     *
     *<p>方法名称:find</p>
     *<p>方法说明:查询collection
     *			默认查50条数据，limit为-1则不限制</p>
     *<p>返回类型:List<DBObject></p>
     *<p>异        常:</p>
     */
    public static List<DBObject> find(String collectionName, Map<String, Object> condition){
        int limit = DEFAULT_LIMIT;
        if(condition.get("limit") != null){
            limit = (Integer) condition.get("limit");
            condition.remove("limit");
        }
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBCursor cursor;
        if(limit == UNLIMIT){
            cursor = collection.find(query);
        }else{
            cursor = collection.find(query).limit(limit);
        }
        return cursor.toArray();
    }

    /**
     *
     *<p>方法名称:find</p>
     *<p>方法说明:查询collection
     *			默认查50条数据，limit为-1则不限制</p>
     *<p>返回类型:List<DBObject></p>
     *<p>异        常:</p>
     */
    public static List<DBObject> find(String collectionName, Map<String, Object> condition, Map<String, Object> sortMap){
        int limit = DEFAULT_LIMIT;
        if(condition.get("limit") != null){
            limit = (Integer) condition.get("limit");
            condition.remove("limit");
        }
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBObject sort = new BasicDBObject(sortMap);
        DBCursor cursor;
        if(limit == UNLIMIT){
            cursor = collection.find(query).sort(sort);
        }else{
            cursor = collection.find(query).limit(limit).sort(sort);
        }
        return cursor.toArray();
    }

    /**
     *
     *<p>方法名称:find</p>
     *<p>方法说明:查询collection指定的列
     *			默认查50条数据，limit为-1则不限制</p>
     *<p>返回类型:List<DBObject></p>
     *<p>异        常:</p>
     */
    public static List<DBObject> findField(String collectionName, Map<String, Object> condition, Map<String, Object> keys){
        int limit = DEFAULT_LIMIT;
        if(condition.get("limit") != null){
            limit = (Integer) condition.get("limit");
            condition.remove("limit");
        }
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBObject key = new BasicDBObject(keys);
        DBCursor cursor;
        if(limit == UNLIMIT){
            cursor = collection.find(query, key);
        }else{
            cursor = collection.find(query, key).limit(limit);
        }
        return cursor.toArray();
    }

    /**
     *
     *<p>方法名称:find</p>
     *<p>方法说明:查询collection指定的列
     *			默认查50条数据，limit为-1则不限制</p>
     *<p>返回类型:List<DBObject></p>
     *<p>异        常:</p>
     */
    public static List<DBObject> findField(String collectionName, Map<String, Object> condition, Map<String, Object> keys, Map<String, Object> sortMap){
        int limit = DEFAULT_LIMIT;
        if(condition.get("limit") != null){
            limit = (Integer) condition.get("limit");
            condition.remove("limit");
        }
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBObject key = new BasicDBObject(keys);
        DBObject sort = new BasicDBObject(sortMap);
        DBCursor cursor;
        if(limit == UNLIMIT){
            cursor = collection.find(query, key).sort(sort);
        }else{
            cursor = collection.find(query, key).limit(limit).sort(sort);
        }
        return cursor.toArray();
    }

    public static boolean update(String collectionName, Map<String, Object> condition, Map<String, Object> updates){
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBObject update = new BasicDBObject(updates);
        collection.update(query, update);
        return true;
    }

    public static boolean update(String collectionName, Map<String, Object> condition, Map<String, Object> updates, Map<String, Object> sortMap){
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        DBObject update = new BasicDBObject(updates);
        DBObject sort = new BasicDBObject(sortMap);
        collection.findAndModify(query, sort, update);
        return true;
    }

    public static boolean remove(String collectionName, Map<String, Object> condition){
        DB db = getDB();
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject(condition);
        collection.remove(query);
        return true;
    }

    public static void main(String[] args) throws Exception{
//        DB db = getDB();
//        DBCollection collection = db.getCollection("t_asset");
//        System.out.println(collection.findOne().toString());
        final DB db = getDB();
        final DBCollection collection = db.getCollection("t_zhongyuan");
        File f = new File("F:\\Desktop\\zhongyuan\\2");
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (final File file : f.listFiles()){
            es.execute(new Runnable() {
                public void run() {
                    try {
                        if(file.getAbsolutePath().contains("zyxf092_")){
//                inset092(collection, file.getAbsolutePath());
                        }else if(file.getAbsolutePath().contains("zyxf098_")){
                            inset098(collection, file.getAbsolutePath());
                            System.out.println(file);
                        }
                    }catch (Exception e){

                    }
                }
            });
            Thread.sleep(100);
        }
        System.in.read();
//        inset098(collection, "F:\\Desktop\\zhongyuan\\2\\zyxf098_20170724.dat");
    }

    public static void inset092(DBCollection collection, String file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        Scanner in = new Scanner(fis, "UTF-8");
        if(!in.hasNextLine()){
            return;
        }else {
            System.out.println(in.nextLine());
        }
        String[] temp;
        BasicDBObject data = new BasicDBObject();
        while (in.hasNextLine()){
            temp = in.nextLine().split("\u0003");
            if (temp.length == 1) continue;
            data.clear();
            data.put("file", "092");
            data.put("assetId", temp[3]);
            data.put("amt", temp[7]);
            data.put("repayType", temp[9]);
            data.put("status", temp[10]);
            data.put("userName", temp[0]);
            data.put("idNo", temp[2]);
            data.put("date", temp[5]);
            collection.insert(data);
        }
        fis.close();
        in.close();
    }

    public static void inset098(DBCollection collection, String file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        Scanner in = new Scanner(fis, "UTF-8");
        if(!in.hasNextLine()){
            return;
        }else {
            System.out.println(in.nextLine());
        }
        String[] temp;
        BasicDBObject data = new BasicDBObject();
        while (in.hasNextLine()){
            temp = in.nextLine().split("\u0003");
            if (temp.length == 1) continue;
            data.clear();
            data.put("file", "098");
            data.put("capitalOrderId", temp[4]);
            data.put("amt", temp[9]);
            data.put("amt_fql", temp[8]);
            data.put("amt_zy", temp[7]);
            data.put("amt_dif", temp[13]);
            data.put("amt_dif_type", temp[12]);
            data.put("repay_term", temp[5]);
            data.put("repay_type", temp[10]);
            data.put("status", temp[11]);
            data.put("userName", temp[0]);
            data.put("idNo", temp[2]);
            data.put("date", temp[6]);
            collection.insert(data);
        }
        fis.close();
        in.close();
    }

}
