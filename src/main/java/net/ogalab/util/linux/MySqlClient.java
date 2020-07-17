/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ogalab.util.linux;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oogasawa
 */
public class MySqlClient {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(MySqlClient.class);
    
    
       // SQLを実行する
    public void executeSQL(String dbName, String tablePath, String createFilePath, String alterFilePath) {
        BashResult res = null;
        String mysqlCom = "mysql -uddbj -ptestddbj " + dbName;

        // Create
        Bash bash = new Bash();
        String com = mysqlCom + " < " + createFilePath;
        logger.info(com);
        res = bash.system(com);
        if (res != null && res.getStderr().length() > 0) {
            String log = res.getStderr();
            logger.info(log);
        }

        // TSVインポート
        com = "mysqlimport -uddbj -ptestddbj --local " + dbName + " " + tablePath;
        logger.info(com);
        res = bash.system(com);
        if (res != null && res.getStderr().length() > 0) {
            String log = res.getStderr();
            logger.info(log);
        }

        // Alter Table
        com = mysqlCom + " < " + alterFilePath;
        logger.info(com);
        res = bash.system(com);
        if (res != null && res.getStderr().length() > 0) {
            String log = res.getStderr();
            logger.info(log);
        }
    }

    // Create文作成
    public String makeCreateSQL(String tableName, ArrayList<String> field_Type) {

        StringBuffer sql = new StringBuffer();
        sql.append("DROP TABLE IF EXISTS " + tableName + ";");
        sql.append("CREATE TABLE " + tableName + " (\n");
        for (int i = 0; i < field_Type.size(); i++) {
            sql.append("    " + field_Type.get(i));
            if (i < field_Type.size() - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }
        sql.append(")\n");
        sql.append("CHARACTER SET 'utf8';\n");
        return sql.toString();
    }

    // Alter文作成
    public String makeAlterSQL(String tableName, ArrayList<String> field_Type, ArrayList<String> primKeyList, ArrayList<String> idxList) {

        StringBuffer sql = new StringBuffer();

        // 主キー追加
        //sql.append("ALTER TABLE " + tableName + " ADD PRIMARY KEY (");
        //for (int i = 0; i < primKeyList.size(); i++) {
        //    if (i > 0) {
        //        sql.append(", ");
        //    }
        //    sql.append(primKeyList.get(i));
        //}
        //sql.append(");\n");

        // インデックス
        if (null != idxList) {
            for (String key : idxList) {
                sql.append("CREATE INDEX " + tableName + "_IDX_" + key + " ON " + tableName + "(" + key + ");\n");
            }
        }

        return sql.toString();
    }

    // ファイルに出力する
    public void outFile(String filePath, String str) {

        File sqlFile = new File(filePath);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(sqlFile)));
            pw.println(str);
            pw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    // jenkins自動ビルドテスト
}
