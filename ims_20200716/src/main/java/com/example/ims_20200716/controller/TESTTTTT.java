package com.example.ims_20200716.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TESTTTTT {
    public static void main(String arg[]) {
//        DataAccess da=null;
//        Connection conn=null;
//        PreparedStatement ps = null;
//        try {
//            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
//            File file = new File("g:/hlr_y.txt");
//            if (file.isFile() && file.exists()) {
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file), encoding);
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTXT = null;
//                da=new DataAccess();
//                conn=da.getConnection();
//                long id=1L;
//                String acc_num="";
//                String ins="";
//                String prp1="";
//                String prp2="";
//                String prp3="";
//                String prp4="";
//                String prp5="";
//                String prp6="";
//                String prp7="";
//                String prp8="";
//                String prp9="";
//                String prp10="";
//                String prp11="";
//                String prp12="";
//                String hh="";
//                String sql="insert into temp_0730_yff1_gfq (ACC_NUM, INSTRUCTION, PRP1, PRP2, PRP3, PRP4, PRP5, PRP6, PRP7, PRP8, PRP9, PRP10, PRP11, PRP12, HH)" +
//                        "values (?,?, ?,?,?,?,?,?, ?, ?,?,?,?,?,?)";
//                conn.setAutoCommit( false );
//                while ((lineTXT = bufferedReader.readLine()) != null) {
//                    ps = conn.prepareStatement(sql);
//
////需将变量清空
//                    acc_num="";
//                    ins="";
//                    prp1="";
//                    prp2="";
//                    prp3="";
//                    prp4="";
//                    prp5="";
//                    prp6="";
//                    prp7="";
//                    prp8="";
//                    prp9="";
//                    prp10="";
//                    prp11="";
//                    prp12="";
//                    hh="";
//                    String str=lineTXT.toString().trim();
//                    String[] s =str.split("\\|");
//                    acc_num=s[0];
//                    ins=s[1];
//                    hh=s[s.length-1];
//                    for(int i=2;i<s.length-1;i+=2){
//                        if("1".equals(s[i])){
//                            prp1=s[i+1];
//                        }else if("2".equals(s[i])){
//                            prp2=s[i+1];
//                        }else if("3".equals(s[i])){
//                            prp3=s[i+1];
//                        }else if("4".equals(s[i])){
//                            prp4=s[i+1];
//                        }else if("5".equals(s[i])){
//                            prp5=s[i+1];
//                        } else if("6".equals(s[i])){
//                            prp6=s[i+1];
//                        }else if("7".equals(s[i])){
//                            prp7=s[i+1];
//                        }else if("8".equals(s[i])){
//                            prp8=s[i+1];
//                        } else if("9".equals(s[i])){
//                            prp9=s[i+1];
//                        }else if("10".equals(s[i])){
//                            prp10=s[i+1];
//                        } else if("11".equals(s[i])){
//                            prp11=s[i+1];
//                        }else if("12".equals(s[i])){
//                            prp12=s[i+1];
//                        }
//                    }
//                    id+=1;
//                    ps.setString(1,acc_num);
//                    ps.setString(2,ins);
//                    ps.setString(3,prp1);
//                    ps.setString(4,prp2);
//                    ps.setString(5,prp3);
//                    ps.setString(6,prp4);
//                    ps.setString(7,prp5);
//                    ps.setString(8,prp6);
//                    ps.setString(9,prp7);
//                    ps.setString(10,prp8);
//                    ps.setString(11,prp9);
//                    ps.setString(12,prp10);
//                    ps.setString(13,prp11);
//                    ps.setString(14,prp12);
//                    ps.setString(15,hh);
//                    ps.addBatch();
//                    ps.addBatch();
//                    if((id)%10000==0){//每一万条提交一次
//                        ps.executeBatch();
//                        ps.clearBatch();
//                        conn.commit();
//                        if (null==conn) { //如果连接关闭了 就在创建一个 为什么要这样 原因是 conn.commit()后可能conn被关闭
//                            conn = da.getConnection();;
//                            conn.setAutoCommit(false);
//                        }
//                        id=0;//提交后ID从0开始
//                    }
//                }
//                ps.executeBatch();
//                conn.commit();//提交最后剩余不足一万条数据
//                read.close();
//                System.out.println("读取完毕！");
//            }else{
//                System.out.println("找不到指定的文件！");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容操作出错");
//            e.printStackTrace();
//        }finally{
//            try {
//                ps.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }
}