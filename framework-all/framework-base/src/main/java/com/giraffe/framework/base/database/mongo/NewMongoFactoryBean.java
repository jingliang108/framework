package com.giraffe.framework.base.database.mongo;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

public class NewMongoFactoryBean extends AbstractFactoryBean<MongoClient> {


    private String mongodbURI;

//    private String database;


    // 是否主从分离(读取从库)，默认读写都在主库
    private boolean readSecondary = false;
    // 设定写策略(出错时是否抛异常)，默认采用SAFE模式(需要抛异常)
    private WriteConcern writeConcern = WriteConcern.SAFE;

    @Override
    protected MongoClient createInstance() throws Exception {
        MongoClient mongo = initMongo();

        // 设定主从分离
        if (readSecondary) {
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }

        // 设定写策略
        mongo.setWriteConcern(writeConcern);
        return mongo;
    }

    /**
     * 初始化mongo实例
     *
     * @return
     * @throws Exception
     */
    private MongoClient initMongo() throws Exception {
        // 根据条件创建MongoClient实例
        //String sURI = String.format("mongodb://%s:%s@%s:%d/%s", "admin", "123", "192.168.32.128", 27017, "aijiu");
//        if (!mongodbURI.endsWith("/")) {
//            mongodbURI += "/";
//        }

        MongoClientURI uri = new MongoClientURI(mongodbURI);
        return new MongoClient(uri);
    }

    @Override
    public Class<?> getObjectType() {
        return MongoClient.class;
    }


    public boolean isReadSecondary() {
        return readSecondary;
    }

    public void setReadSecondary(boolean readSecondary) {
        this.readSecondary = readSecondary;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public String getMongodbURI() {
        return mongodbURI;
    }

    public void setMongodbURI(String mongodbURI) {
        this.mongodbURI = mongodbURI;
    }

//    public String getDatabase() {
//        return database;
//    }
//
//    public void setDatabase(String database) {
//        this.database = database;
//    }
}
