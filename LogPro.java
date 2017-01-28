/**
  * Time: 2017/1/18 13:35
 * User: Tusdasa翼
 *
 */
import java.io.*;
import java.util.Vector;
public class LogPro {
	//使用的变量
    private String ip="";
    //默认访问次数
    private int times=1;
    //ip
    public Vector<String> ipqueue =new Vector<String>();
    //次数
    public Vector<Integer> iptimes =new Vector<Integer>();
    //读入文件
    LogPro(String filename){
        try{
            StringBuffer sb=new StringBuffer();
            FileReader reader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(reader);

            while ((this.ip=bufferedReader.readLine())!=null) {
                sb.append(this.ip + "/n");

                String temp = "";
                String[] strings = ip.split(" ");
                temp = strings[7];
                String[] strings1 = temp.split("]");
                this.ipqueue.add(strings1[0].trim());
                //System.out.println(strings1[0].toString());
            }
            bufferedReader.close();
            reader.close();
            this.StringComparative();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    //处理访问次数
    private void StringComparative(){
        int temp=0;
        while (temp < 1){
                for (int i=0;i<this.ipqueue.size();i++) {
                    for (int j = i+1; j < this.ipqueue.size(); j++) {
                        if (this.ipqueue.get(i).equals(this.ipqueue.get(j))) {
                            this.times++;
                            this.ipqueue.remove(j);
                            j=i;
                        }
                    }

                    if (this.iptimes.size()<=this.ipqueue.size()){
                        this.iptimes.add(i,this.times);
                    }

                    times = 1;
                }
                temp++;
        }

    }
	//获得大小
    public int getipsize(){
        if (this.iptimes.size()==this.ipqueue.size())
            return ipqueue.size();
        else
            return 0;

    }
}
