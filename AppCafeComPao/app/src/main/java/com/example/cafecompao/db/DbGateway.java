package com.example.cafecompao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbGateway {

    private static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway(Context ctx){
        Conexao con = new Conexao(ctx);
        db = con.getWritableDatabase();
    }

    public static DbGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DbGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}
