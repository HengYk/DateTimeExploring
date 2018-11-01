package cn.edu.xidian.ictt.yk.exec;

import cn.edu.xidian.ictt.yk.util.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by heart_sunny on 2018/11/1
 */
public class CRUDTestPS {

    public static void main(String[] args) throws SQLException {

        System.out.println("-----------------------------------------------");

        addDateTime();

        System.out.println("-----------------------------------------------");

        getDateTime();

        System.out.println("-----------------------------------------------");
    }

    public static void addDateTime() throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            //注意：datetime 和 datetime2 两个字段在数据库中的类型都是DATETIME
            String sql = "insert into t1(dt, dt2, dt3, dt4) values(?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            java.util.Date utilDate = new java.util.Date();
            System.out.println("utilDate: " + utilDate + " | type = java.util.Date");
            System.out.println("utilDate.getTime(): " + utilDate.getTime() + " | type = long");

            // String ---> DATETIME
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String formatDate = sdf.format(utilDate);
            System.out.println("formatDate: " + formatDate + " | type = String");
            ps.setString(1, formatDate);

            // java.sql.Date ---> DATETIME
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            System.out.println("sqlDate: " + sqlDate + " | type = java.sql.Date");
            ps.setDate(2, sqlDate);

            // java.sql.Time ---> DATETIME 不可行
            Time time = new Time(utilDate.getTime());
            System.out.println("time: " + time + " | type = java.sql.Time");
            //ps.setTime(3, time);
            ps.setString(3, null);

            // java.sql.Timestamp ---> DATETIME
            Timestamp timestamp = new Timestamp(utilDate.getTime());
            System.out.println("timestamp: " + timestamp + " | type = java.sql.Timestamp");
            ps.setTimestamp(4, timestamp);

            ps.executeUpdate();

        } finally {
            DBConnection.close(rs, ps, conn);
        }
    }

    public static void getDateTime() throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Date birthday = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "select id, dt, dt2, dt3, dt4 from t1";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String getFormatData = rs.getString("dt");
                System.out.println("getFormatData: " + getFormatData + " | type = String");

                Date getSqlData = rs.getDate("dt2");
                System.out.println("getSqlData: " + getSqlData + " | type = java.sql.Date");

                String getEmptyString = rs.getString("dt3");
                System.out.println("getEmptyString: " + getEmptyString + " | type = String");

                Timestamp getTimestamp = rs.getTimestamp("dt4");
                System.out.println("getTimestamp: " + getTimestamp + " | type = java.sql.Timestamp");

            }
        } finally {
            DBConnection.close(rs, ps, conn);
        }
    }
}
