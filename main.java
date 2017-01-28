/**
 * Program: Apache_Log
 * Time: 2017/1/18 13:35
 * User: Tusdasa翼
 */
public class main {
    public static void main(String[] args) {

        if (args[0]==null){
            System.out.println("请键入日志位置");
            System.exit(10);
        }
            LogPro logPro = new LogPro(args[0]);
            DataBase dataBase = new DataBase();
            dataBase.GetConnectionInfo();
            dataBase.SetSqlValue();

        if (dataBase.TestConnection()) {
            System.out.println("MySQL链接成功");
            dataBase.Select(dataBase.db_ipqueue, dataBase.db_iptimes);
            if (dataBase.db_ipqueue.size()>0){
                dataBase.StringLink(logPro.ipqueue, logPro.iptimes, dataBase.db_ipqueue, dataBase.db_iptimes);
                System.out.println("成功更新" + dataBase.Update(dataBase.ex_ip, dataBase.ex_ti, dataBase.ex_ip.size()) + "条记录");
                System.out.println("成功增加"+dataBase.Insert(logPro.ipqueue,logPro.iptimes,logPro.ipqueue.size())+"条记录");
            }else {
                System.out.println("检查到是第一次运行");
                System.out.println("成功写入"+dataBase.Insert(logPro.ipqueue, logPro.iptimes, logPro.getipsize())+"条记录");
            }
    }else{
            System.out.println("MySQL链接失败!");
            System.exit(10);
        }
       }
    }
