import lombok.SneakyThrows;

import java.io.*;
import java.sql.*;


/*
我这真是尼玛一坨屎,写了半天发现只要右键文件可以直接导入我写你妈呢?我真是最蠢的傻逼
 */
*/
 */
public class importxt {
    @SneakyThrows
    public static void main(String[] args) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/webapp","root","1234");
        Statement stat = conn.createStatement();;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Yaoboyu\\Desktop\\love\\webapp\\src\\main\\resources\\wword.txt")));
        String line;
        String sql = "insert into wordlistl1(id, lasttime,word,mean,count) VALUES (?,null,?,?,0)";
        PreparedStatement ps = conn.prepareStatement(sql);
        while((line = in.readLine()) != null){
            int id = Integer.parseInt(line.substring(0,line.indexOf('.')));
            String word = line.substring(line.indexOf('.') + 1,line.indexOf('?'));
            String mean = line.substring(line.indexOf('?') + 1,line.length());
            /*ps.setObject(1,id);
            ps.setObject(2,word);
            ps.setObject(3,mean);
            ps.execute();*/

        }
    }
}
