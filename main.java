/**
 * Program: Apache_Log
 * Time: 2017/1/18 13:35
 * User: Tusdasa��
 */
public class main {
    public static void main(String[] args) {

        if (args[0]==null){
            System.out.println("�������־λ��");
            System.exit(10);
        }
            LogPro logPro = new LogPro(args[0]);
            DataBase dataBase = new DataBase();
            dataBase.GetConnectionInfo();
            dataBase.SetSqlValue();

        if (dataBase.TestConnection()) {
            System.out.println("MySQL���ӳɹ�");
            dataBase.Select(dataBase.db_ipqueue, dataBase.db_iptimes);
            if (dataBase.db_ipqueue.size()>0){
                dataBase.StringLink(logPro.ipqueue, logPro.iptimes, dataBase.db_ipqueue, dataBase.db_iptimes);
                System.out.println("�ɹ�����" + dataBase.Update(dataBase.ex_ip, dataBase.ex_ti, dataBase.ex_ip.size()) + "����¼");
                System.out.println("�ɹ�����"+dataBase.Insert(logPro.ipqueue,logPro.iptimes,logPro.ipqueue.size())+"����¼");
            }else {
                System.out.println("��鵽�ǵ�һ������");
                System.out.println("�ɹ�д��"+dataBase.Insert(logPro.ipqueue, logPro.iptimes, logPro.getipsize())+"����¼");
            }
    }else{
            System.out.println("MySQL����ʧ��!");
            System.exit(10);
        }
       }
    }
