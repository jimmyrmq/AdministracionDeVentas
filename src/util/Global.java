package util;

import com.djm.db.connection.Connection;

public class Global {
    private static Global global = null;
    private Connection conn = null;


    private Global(){}

    public static Global getInstance(){

        if(global == null){
            global = new Global();
        }

        return global;
    }

    public Connection getConnection(){
        return conn;
    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }
}
