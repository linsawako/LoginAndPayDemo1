package com.example.loginandpaytools.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loginandpaytools.bean.User;
import com.example.loginandpaytools.bean.DBUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jerry on 17-2-22.
 */

public class DataBaseHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "user.database.1.0";
    public static final String USER_TABLE_NAME = "user_table";

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;
    private static final String mSecretKey = "12345678";

    public DataBaseHelper(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
        establishDb();
    }

    /**
     * 打开数据库
     */
    private void establishDb() {
        if (this.db == null) {
            this.db = this.dbOpenHelper.getWritableDatabase();
        }
    }

    /**
     * 插入一条记录
     * @return 插入记录的id -1表示插入不成功
     */
    public long insertOrUpdate(String userName, String password, String date, int isSaved) {
        password = AesEncipher(password);
        boolean isUpdate = false;
        List<User> list = queryAllUserName();
        for (int i = 0; i < list.size(); i++) {
            if (userName.equals(list.get(i).getUserName())) {
                isUpdate = true;
                break;
            }
        }
        long id = -1;
        if (isUpdate) {
            id = update(userName, password, date, isSaved);
        } else {
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put(DBUser.User.USERNAME, userName);
                values.put(DBUser.User.PASSWORD, password);
                values.put(DBUser.User.DATE, date);
                values.put(DBUser.User.ISSAVED, isSaved);
                id = db.insert(USER_TABLE_NAME, null, values);
            }
        }
        return id;
    }

    /**
     * 删除一条记录
     * @param userName 用户名
     * @return 删除记录的id -1表示删除不成功
     */
    public long delete(String userName) {
        long id = db.delete(USER_TABLE_NAME, DBUser.User.USERNAME + " = '" + userName
                + "'", null);
        return id;
    }

    /**
     * 更新一条记录
     * @param date
     * @return 更新过后记录的id -1表示更新不成功
     */
    public long update(String username, String password, String date, int isSaved) {
        ContentValues values = new ContentValues();
        values.put(DBUser.User.USERNAME, username);
        values.put(DBUser.User.PASSWORD, password);
        values.put(DBUser.User.DATE, date);
        values.put(DBUser.User.ISSAVED, isSaved);
        long id = db.update(USER_TABLE_NAME, values, DBUser.User.USERNAME + " = '"
                + username + "'", null);
        return id;
    }


    /**
     * 查询所有用户名
     * @return 所有记录的list集合
     */
    public List<User> queryAllUserName() {
        if (db != null) {
            Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
                    null, null);
            int count = cursor.getCount();
            List<User> list = new ArrayList<>();
            if (count > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    User user = new User();
                    user.setUserName(cursor.getString(cursor
                            .getColumnIndex(DBUser.User.USERNAME)));
                    user.setData(cursor.getString(cursor.getColumnIndex(DBUser.User.DATE)));
                    String password = AesDecrypt(cursor.getString(cursor.getColumnIndex(DBUser.User.PASSWORD)));
                    user.setPassword(password);
                    list.add(user);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return list;
        } else {

            return new ArrayList<>();
        }

    }

    /**
     * 关闭数据库
     */
    public void cleanUp() {
        if (this.db != null) {
            this.db.close();
            this.db = null;
        }
    }

    /**
     * 数据库辅助类
     */
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + USER_TABLE_NAME + " (" + DBUser.User._ID
                    + " integer primary key," + DBUser.User.USERNAME + " text, "
                    + DBUser.User.PASSWORD + " text, " + DBUser.User.DATE + " text, " +
                    DBUser.User.ISSAVED + " INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
        }

    }

    private String AesEncipher(String password){
        String encryPassword = AesUtil.encrypt(mSecretKey, password);
        return encryPassword;
    }

    private String AesDecrypt(String encryPassword){
        String decryPassword = AesUtil.decrypt(mSecretKey, encryPassword);
        return decryPassword;
    }
}
