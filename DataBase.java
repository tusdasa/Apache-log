/**
 * Time: 2017/1/18
 * User: Tusdasa翼
 *
 */

import java.io.*;
import java.sql.*;
import java.util.Vector;

public class DataBase {
    public Vector<String> db_ipqueue =new Vector<String>();
    public Vector<Integer> db_iptimes =new Vector<Integer>();
    public Vector<String> ex_ip =new Vector<>();
    public Vector<Integer> ex_ti =new Vector<>();
    private String db_url="";
    private String db_user="";
    private String db_pass="";
    private String db_name_base="";
    private String db_name_table="";
    private String[] strings=new String[5];


    public void GetConnectionInfo(){
        String temp="";
        int i=0;
        try {
            StringBuffer stringBuffer=new StringBuffer();
         FileReader fr= new FileReader("config.ini");
         BufferedReader bufferedReader=new BufferedReader(fr);
         while ((temp=bufferedReader.readLine())!=null){
            stringBuffer.append(temp+"/n");
            String[] strarr=temp.split("=");
             strings[i]=strarr[1];
                 i++;
         }

         bufferedReader.close();
         fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("config.ini file not found!");
        } catch (IOException e) {
            System.out.println("purview error!");
        }

    }

    public void SetSqlValue(){
        this.db_user=this.strings[1];
        this.db_pass=this.strings[2];
        this.db_name_base=this.strings[3];
        this.db_url="jdbc:mysql://"+this.strings[0]+"/"+this.db_name_base+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        this.db_name_table=this.strings[4];
    }

    public boolean TestConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print("Not Found Driver   ");
            System.out.println("Please install MySQL for Java Driver! ");
            System.exit(11);
        }

        try{
            Connection connection=(Connection) DriverManager.getConnection(db_url,db_user,db_pass);
            connection.close();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int Insert(Vector<String> ipqueue,Vector<Integer> iptimes,Vector<String> location,int size){
        String qurry="";
        int num=0;
        try {
            Connection connection=(Connection) DriverManager.getConnection(db_url,db_user,db_pass);
            Statement statement=(Statement)connection.createStatement();
            for (int i=0;i<size;i++){
             qurry="INSERT INTO"+" "+db_name_table+"(ip,times,location)"+" "+"VALUES("+"\'"+ipqueue.get(i).toString()+"\'"+","+"\'"+iptimes.get(i).intValue()+"\'"+","+"\'"+location.get(i).toString()+"\'"+");";
             num+=statement.executeUpdate(qurry);
            }

			statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("insert execution fail!");
        }
        return num;
    }

    public void Select(Vector<String> db_ipqueue, Vector<Integer> db_iptimes){
        int count=0;
        String qurry="SELECT ip,times FROM "+db_name_table+" "+"ORDER BY times"+";";
        try {
            Connection connection=(Connection) DriverManager.getConnection(db_url,db_user,db_pass);
            Statement statement=(Statement)connection.createStatement();
            ResultSet Rs=statement.executeQuery(qurry);
            while (Rs.next()){
                db_ipqueue.add(count,Rs.getString("ip"));
                db_iptimes.add(count,Rs.getInt("times"));
            }
            Rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("select execution fail!");
        }
    }


    public int Update(Vector<String> ex_ip,Vector<Integer> ex_ti,int size){
        String qurry="";
        int rs=0;
        try {
            Connection connection=(Connection) DriverManager.getConnection(db_url,db_user,db_pass);
            Statement statement=(Statement)connection.createStatement();
            for (int i=0;i<size;i++){
                qurry="UPDATE"+" "+db_name_table+" "+"SET"+" "+"times="+ex_ti.get(i)+" "+"WHERE "+"ip="+"\""+ex_ip.get(i)+"\""+";";
                rs+=statement.executeUpdate(qurry);
            }

			statement.close();
			connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("update execution fail!");
        }
        return rs;
    }

    public void IPLocation(Vector<String> ipqueue,Vector<String> location){
    	String qurry="";
    	try{
    		Connection connection=(Connection) DriverManager.getConnection(db_url,db_user,db_pass);
            Statement statement=(Statement)connection.createStatement();
            for(int i=0;i<ipqueue.size();i++){
            qurry="SELECT Country,`Local` FROM ip_adder WHERE INET_ATON('"+ipqueue.get(i).toString()+"') BETWEEN INET_ATON(StartIP) AND INET_ATON(EndIP);";
            ResultSet Rs=statement.executeQuery(qurry);
            while(Rs.next()){
            	String Country=Rs.getString("Country");
            	String Local=Rs.getString("Local");
            	location.add(Country+"  "+Local);
            }
            }

            statement.close();
            connection.close();

    	}catch(SQLException e){
    		e.printStackTrace();
    		System.out.println("location selesct error!");
    	}

    }

    public void StringLink(Vector<String> ipqueue,Vector<Integer> iptimes,Vector<String> db_ipqueue,Vector<Integer> db_iptimes){

           for (int i=0;i<db_ipqueue.size();i++)
               for (int j=0;j<ipqueue.size();j++){
                    if (db_ipqueue.get(i).equals(ipqueue.get(j))){
                        ex_ip.add(db_ipqueue.get(i));
                        ex_ti.add(iptimes.get(j)+db_iptimes.get(i));
                        ipqueue.remove(j);
                        iptimes.remove(j);
                    }
               }


    }



    public int getSize(){
       if (this.db_ipqueue.size()==this.db_iptimes.size())
           return db_ipqueue.size();
       else
           return 0;
    }

}
