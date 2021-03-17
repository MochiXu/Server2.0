package Servlet.Analyze;

import Database.DBconnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static Servlet.Config.Scenic_Max_PersonCount;

@WebServlet(name = "Servlet_analyze")
public class Servlet_analyze extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        switch (Integer.parseInt(request.getParameter("forecast"))){
            case 1:
                try {
                    printWriter.print(get_line1_01());
                } catch (SQLException | ParseException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    printWriter.print(get_line1_02());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    printWriter.print(get_line1_03());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    printWriter.print(get_line2_01());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    printWriter.print(get_line2_02());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    printWriter.print(get_line3_01());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    printWriter.print(get_line3_02());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    printWriter.print(get_chart3_Json());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                try {
                    printWriter.print(get_chart4_Json());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    //line1-01，今日景区接纳游客同昨日相比【增加/减少】【百分比】
    private String get_line1_01() throws SQLException, ClassNotFoundException, ParseException {
        //获取今日数据库内已经统计得出的景区游客总人数 数据段的起始与结束时间点
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_scenic_person_sum_begin_end_time();\n");
        String begin_time="";
        String end_time="";
        String today_date="";
        while (resultSet1.next()){
            begin_time=resultSet1.getString(2);
            end_time=resultSet1.getString(1);
            today_date=resultSet1.getString(3);
        }
        dBconnection1.FreeResource();

        //获取前一天日期
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date Today = Date_Format.parse(today_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date Yesterday=calendar.getTime();
        String Yesterday_date=Date_Format.format(Yesterday);//这里获取到了数据库内前一天的日期

        //利用上述时间区间获取前一天的景区游客平均人数，若得到的数据集为空，则设置为0
        DBconnection dBconnection2=new DBconnection();
        ResultSet resultSet2=dBconnection2.DB_FindDataSet("call calculate_Average_Scenic('"+begin_time+"','"+end_time+"','"+Yesterday_date+"');\n");
        double Yesterday_Average=0;
        while (resultSet2.next()){
            Yesterday_Average=resultSet2.getDouble(1);
        }
        dBconnection2.FreeResource();

        //利用上述时间区段获取数据库内最新一天的景区游客平均人数，这里数据集不可能为空
        DBconnection dBconnection3=new DBconnection();
        ResultSet resultSet3=dBconnection3.DB_FindDataSet("call calculate_Average_Scenic('"+begin_time+"','"+end_time+"','"+today_date+"');\n");
        double Today_Average=0;
        while (resultSet3.next()){
            Today_Average=resultSet3.getDouble(1);
        }
        dBconnection3.FreeResource();

        //计算增长率
        if(Today_Average>=Yesterday_Average){
            //人流量在增加

            double tip=(Today_Average-Yesterday_Average)/Yesterday_Average;
            System.out.println("计算增长率"+tip);
            return "增加了"+tip*100+"%";
        }
        else {
            //人流量在jianshao
            double tip=(Yesterday_Average-Today_Average)/Yesterday_Average;
            System.out.println("计算率降低"+tip);
            return "减少了"+tip*100+"%";
        }
    }

    //line1-02，近期日流量变化趋势（最近5日）
    private String get_line1_02() throws SQLException, ClassNotFoundException, ParseException {
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_scenic_person_sum_begin_end_time();\n");
        String today_date="";
        while (resultSet1.next()){
            today_date=resultSet1.getString(3);
        }
        dBconnection1.FreeResource();

        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date day5 = Date_Format.parse(today_date);
        String Day5=Date_Format.format(day5);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day5);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date day4=calendar.getTime();
        String Day4=Date_Format.format(day4);


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(day4);
        calendar2.add(Calendar.DAY_OF_MONTH, -1);
        Date day3=calendar2.getTime();
        String Day3=Date_Format.format(day3);


        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(day3);
        calendar3.add(Calendar.DAY_OF_MONTH, -1);
        Date day2=calendar3.getTime();
        String Day2=Date_Format.format(day2);


        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(day2);
        calendar4.add(Calendar.DAY_OF_MONTH, -1);
        Date day1=calendar4.getTime();
        String Day1=Date_Format.format(day1);

        double day1_av=0,day2_av=0,day3_av=0,day4_av=0,day5_av=0;

        DBconnection dBconnection2=new DBconnection();
        ResultSet resultSet2=dBconnection2.DB_FindDataSet("call calculate_Average_Scenic_Daily('"+Day1+"');\n");
        while (resultSet2.next()){
            day1_av=resultSet2.getDouble(1);
        }
        dBconnection2.FreeResource();

        DBconnection dBconnection3=new DBconnection();
        ResultSet resultSet3=dBconnection3.DB_FindDataSet("call calculate_Average_Scenic_Daily('"+Day2+"');\n");
        while (resultSet3.next()){
            day2_av=resultSet3.getDouble(1);
        }
        dBconnection3.FreeResource();

        DBconnection dBconnection4=new DBconnection();
        ResultSet resultSet4=dBconnection4.DB_FindDataSet("call calculate_Average_Scenic_Daily('"+Day3+"');\n");
        while (resultSet4.next()){
            day3_av=resultSet4.getDouble(1);
        }
        dBconnection4.FreeResource();

        DBconnection dBconnection5=new DBconnection();
        ResultSet resultSet5=dBconnection5.DB_FindDataSet("call calculate_Average_Scenic_Daily('"+Day4+"');\n");
        while (resultSet5.next()){
            day4_av=resultSet5.getDouble(1);
        }
        dBconnection5.FreeResource();

        DBconnection dBconnection6=new DBconnection();
        ResultSet resultSet6=dBconnection6.DB_FindDataSet("call calculate_Average_Scenic_Daily('"+Day5+"');\n");
        while (resultSet6.next()){
            day5_av=resultSet6.getDouble(1);
        }
        dBconnection6.FreeResource();

        //判断是否一直在增长
        int rise=0;
        //判断是否一直在减小
        int down=0;

        if (day5_av>=day4_av&&day4_av>=day3_av&&day3_av>=day2_av&&day2_av>=day1_av) {
            rise = 1;
        }
        else if(day5_av<=day4_av&&day4_av<=day3_av&&day3_av<=day2_av&&day2_av<=day1_av){
            down=1;
        }
        //计算方差
        double[] data = {day1_av,day2_av,day3_av,day4_av,day5_av};
        double pop_std=POP_STD_dev(data);

        System.out.println("计算方差"+data);
        String front="";
        if(rise==1){
            front="一直爬升，且";
        }
        if(down==1){
            front="一直下降，且";
        }
        if(pop_std>10){
            return front+"波动幅度大";
        }else {
            return front+"波动幅度小";
        }
    }

    //line1-03，未来三日客流量预测平均值以及波动数据
    private String get_line1_03() throws SQLException, ClassNotFoundException, ParseException {
        System.out.println("回传未来三日客流量平均值及波动数据");
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_scenic_person_sum_begin_end_time();\n");
        String today_date="";
        while (resultSet1.next()){
            today_date=resultSet1.getString(3);
        }
        dBconnection1.FreeResource();

        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Date_Format.parse(today_date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date day1=calendar.getTime();
        String Day1=Date_Format.format(day1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(day1);
        calendar2.add(Calendar.DAY_OF_MONTH, +1);
        Date day2=calendar2.getTime();
        String Day2=Date_Format.format(day2);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(day2);
        calendar3.add(Calendar.DAY_OF_MONTH, +1);
        Date day3=calendar3.getTime();
        String Day3=Date_Format.format(day3);

        double day1_av=0,day2_av=0,day3_av=0;
        DBconnection dBconnection2=new DBconnection();
        ResultSet resultSet2=dBconnection2.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day1+"');\n");
        while (resultSet2.next()){
            day1_av=resultSet2.getDouble(1);
        }
        dBconnection2.FreeResource();

        DBconnection dBconnection3=new DBconnection();
        ResultSet resultSet3=dBconnection3.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day2+"');\n");
        while (resultSet3.next()){
            day2_av=resultSet3.getDouble(1);
        }
        dBconnection3.FreeResource();

        DBconnection dBconnection4=new DBconnection();
        ResultSet resultSet4=dBconnection4.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day3+"');\n");
        while (resultSet4.next()){
            day3_av=resultSet4.getDouble(1);
        }
        dBconnection4.FreeResource();

        int average=(int)(day1_av+day2_av+day3_av)/3;
        //计算方差
        double[] data = {day1_av,day2_av,day3_av};
        double pop_std=POP_STD_dev(data);

        if (pop_std>=10) {
            return average + "人次左右，且波动幅度较大";
        }else {
            return average + "人次左右，且波动幅度较小";
        }
    }

    //line2-01，未来三日客流量预测实际数据
    private String get_line2_01() throws SQLException, ClassNotFoundException, ParseException {
        System.out.println("回传未来三日客流量实际预测数据");
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_scenic_person_sum_begin_end_time();\n");
        String today_date="";
        while (resultSet1.next()){
            today_date=resultSet1.getString(3);
        }
        dBconnection1.FreeResource();

        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Date_Format.parse(today_date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date day1=calendar.getTime();
        String Day1=Date_Format.format(day1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(day1);
        calendar2.add(Calendar.DAY_OF_MONTH, +1);
        Date day2=calendar2.getTime();
        String Day2=Date_Format.format(day2);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(day2);
        calendar3.add(Calendar.DAY_OF_MONTH, +1);
        Date day3=calendar3.getTime();
        String Day3=Date_Format.format(day3);

        double day1_av=0,day2_av=0,day3_av=0;
        DBconnection dBconnection2=new DBconnection();
        ResultSet resultSet2=dBconnection2.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day1+"');\n");
        while (resultSet2.next()){
            day1_av=resultSet2.getDouble(1);
        }
        dBconnection2.FreeResource();

        DBconnection dBconnection3=new DBconnection();
        ResultSet resultSet3=dBconnection3.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day2+"');\n");
        while (resultSet3.next()){
            day2_av=resultSet3.getDouble(1);
        }
        dBconnection3.FreeResource();

        DBconnection dBconnection4=new DBconnection();
        ResultSet resultSet4=dBconnection4.DB_FindDataSet("call calculate_Average_Scenic_Daily_Forecast('"+Day3+"');\n");
        while (resultSet4.next()){
            day3_av=resultSet4.getDouble(1);
        }
        dBconnection4.FreeResource();


        return Day1+": "+(int)day1_av+"人次, "+Day2+": "+(int)day2_av+"人次, "+Day3+": "+(int)day3_av+"人次。 ";
    }

    //line2-02，景区客流量实时饱和度
    private String get_line2_02() throws SQLException, ClassNotFoundException {
        System.out.println("统计饱和度");
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet =dBconnection.DB_FindDataSet("call get_Current_Scenic_Sum();\n");
        int sum=0;
        while (resultSet.next()){
            sum=resultSet.getInt(1);
        }
        dBconnection.FreeResource();
        double per=((float)sum/Scenic_Max_PersonCount)*100;
        String Per=String.format("%.2f", per);
//        System.out.println(sum+"   "+Scenic_Max_PersonCount+"   "+sum/200);
        return Per+"%";
    }

    //line3-01，景点资源承受压力情况分析
    private String get_line3_01() throws SQLException, ClassNotFoundException {
        System.out.println("分析资源压力");
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,scenic_name,max_flow from scenic_info;\n");
        resultSet.last();
        int size=resultSet.getRow();
        resultSet.beforeFirst();
        System.out.println("size1:"+size);
        while (resultSet.next()){
            if(resultSet.getString(1).equals("scenic")){
                size--;
            }
        }
        resultSet.beforeFirst();

        System.out.println("size2:"+size);
        //声明数组
        int[] scenic_flow_max = new int[size];
        String[] scenic_id = new String[size];
        String[] scenic_name = new String[size];
        int[] scenic_actual_count = new int[size];
        double[] percent =new double[size];
        int i=0;
        while (resultSet.next()){
            if(!resultSet.getString(1).equals("scenic")) {
                scenic_id[i] = resultSet.getString(1);
                scenic_name[i] = resultSet.getString(2);
                scenic_flow_max[i] = resultSet.getInt(3);
                i++;
            }
        }
        dBconnection.FreeResource();

        for (i=0;i<size;i++){
            System.out.println(scenic_id[i]+" "+scenic_name[i]+" "+scenic_flow_max[i]);
        }
        int end=0;
        //获取到各个景点的实时数据
        for( i=0;i<size;i++){
            DBconnection dBconnection1=new DBconnection();
            ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_Current_Flow_Scenic('"+scenic_id[i]+"');\n");
            while (resultSet1.next()){
                scenic_actual_count[i]=resultSet1.getInt(1);
            }
            percent[i]=(float)scenic_actual_count[i]/scenic_flow_max[i];
            if(percent[i]>0.8)
                end=1;
            dBconnection1.FreeResource();
        }

        String END="";
        if(end==1){
            END="有些景点的人流量较高，可能会超出景点的最大人流量限制，请合理控制门票售卖";
        }else {
            END="各景点人流均处于理想范围，游客体验感将保持最佳";
        }
        String FRONT="";
        for (i=0;i<size;i++){
            FRONT=FRONT+scenic_name[i]+": "+String.format("%.2f", percent[i]*100)+"%";
            if(i!=size-1){
                FRONT=FRONT+"，";
            }else {
                FRONT=FRONT+"。";
            }
        }
        return FRONT+END;

    }

    //line3-02，景点资源压力超出85%的景点
    private String get_line3_02() throws SQLException, ClassNotFoundException{
        System.out.println("计算资源压力大于85% 的景点");
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,scenic_name,max_flow from scenic_info;\n");
        resultSet.last();
        int size=resultSet.getRow();
        resultSet.beforeFirst();
        System.out.println("size1:"+size);
        while (resultSet.next()){
            if(resultSet.getString(1).equals("scenic")){
                size--;
            }
        }
        resultSet.beforeFirst();

        System.out.println("size2:"+size);
        //声明数组
        int[] scenic_flow_max = new int[size];
        String[] scenic_id = new String[size];
        String[] scenic_name = new String[size];
        int[] scenic_actual_count = new int[size];
        double[] percent =new double[size];
        int i=0;
        while (resultSet.next()){
            if(!resultSet.getString(1).equals("scenic")) {
                scenic_id[i] = resultSet.getString(1);
                scenic_name[i] = resultSet.getString(2);
                scenic_flow_max[i] = resultSet.getInt(3);
                i++;
            }
        }
        dBconnection.FreeResource();

        for (i=0;i<size;i++){
            System.out.println(scenic_id[i]+" "+scenic_name[i]+" "+scenic_flow_max[i]);
        }
        //获取到各个景点的实时数据
        for( i=0;i<size;i++){
            DBconnection dBconnection1=new DBconnection();
            ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_Current_Flow_Scenic('"+scenic_id[i]+"');\n");
            while (resultSet1.next()){
                scenic_actual_count[i]=resultSet1.getInt(1);
            }
            percent[i]=(float)scenic_actual_count[i]/scenic_flow_max[i];
            dBconnection1.FreeResource();
        }
        int[] out_of_Max_index = new int[size];
        for (int j=0;j<size;j++){
            out_of_Max_index[j]=-1;
        }
        int WH=0;
        for (i=0;i<size;i++){
            if(percent[i]>0.85){
                WH=1;
                out_of_Max_index[i]=i;
            }
        }
        StringBuilder RES= new StringBuilder();
        for(i=0;i<size;i++){
            if(out_of_Max_index[i]!=-1){
                RES.append(scenic_name[i]);
            }
            if(i==size-1){
                RES.append("");
            }else {
                RES.append("，");
            }
        }
        if(WH==1){
            return RES.toString();
        }else {
            return "暂无";
        }


    }

    //以下为工具函数
    double Sum(double[] data) {
        System.out.println("利用Sum工具");
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum = sum + data[i];
        return sum;
    }

    double Mean(double[] data) {
        System.out.println("利用Mean工具");
        double mean = 0;
        mean = Sum(data) / data.length;
        return mean;
    }

    // population variance 总体方差
    double POP_Variance(double[] data) {
        System.out.println("计算总体方差");
        double variance = 0;
        for (int i = 0; i < data.length; i++) {
            variance = variance + (Math.pow((data[i] - Mean(data)), 2));
        }
        variance = variance / data.length;
        return variance;
    }

    // population standard deviation 总体标准差
    double POP_STD_dev(double[] data) {
        double std_dev;
        std_dev = Math.sqrt(POP_Variance(data));
        return std_dev;
    }

    //sample variance 样本方差
    double Sample_Variance(double[] data) {
        System.out.println("计算样本方差");
        double variance = 0;
        for (int i = 0; i < data.length; i++) {
            variance = variance + (Math.pow((data[i] - Mean(data)), 2));
        }
        variance = variance / (data.length-1);
        return variance;
    }

    // sample standard deviation 样本标准差
    double Sample_STD_dev(double[] data) {
        double std_dev;
        std_dev = Math.sqrt(Sample_Variance(data));
        return std_dev;
    }


    //图表3
    private JSONArray get_chart3_Json() throws SQLException, ClassNotFoundException, ParseException {
        System.out.println("绘制图表3");
        //声明Json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        //获取到当前日期（为了避免数据库的主机日期和前端主机日期不一致，这里日期以数据库为准）
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("call get_scenic_person_sum_begin_end_time();\n");
        String today_date="";
        while (resultSet1.next()){
            today_date=resultSet1.getString(3);
        }
        dBconnection1.FreeResource();

        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Date_Format.parse(today_date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, +3);
        Date day_add3=calendar.getTime();
        String Day3=Date_Format.format(day_add3);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(today);
        calendar2.add(Calendar.DAY_OF_MONTH, -7);
        Date day_min7=calendar2.getTime();
        String Day7=Date_Format.format(day_min7);


        //首先获取到历史数据
        DBconnection dBconnection03=new DBconnection();
        ResultSet resultSet03=dBconnection03.DB_FindDataSet("call get_Json_chart3('"+Day7+"');");
        while (resultSet03.next()){
            jsonObj.put("date",resultSet03.getString(1));
            jsonObj.put("peak_num",resultSet03.getString(2));
            jsonArray.add(jsonObj);
        }
        dBconnection03.FreeResource();

        //再获取到未来三日预测数据
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("call get_Json4_chart3('"+Day3+"');\n");
        while (resultSet.next()){
            jsonObj.put("date",resultSet.getString(1));
            jsonObj.put("peak_num",resultSet.getString(2));
            jsonArray.add(jsonObj);
        }
        dBconnection.FreeResource();
        return jsonArray;
    }

    //图表4
    private JSONArray get_chart4_Json() throws SQLException, ClassNotFoundException, ParseException {
        //首先获取到数据库内最新的日期
        System.out.println("绘制图表4");
        String now_day="";
        DBconnection dBconnection0=new DBconnection();
        ResultSet resultSet0=dBconnection0.DB_FindDataSet("select max(record_date)from flow_actual_time;\n");
        while (resultSet0.next()){
            now_day=resultSet0.getString(1);
        }
        dBconnection0.FreeResource();
        //获取30天之前的时间
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Date_Format.parse(now_day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date day30=calendar.getTime();
        String Day30=Date_Format.format(day30);

        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("select * from scenic_info where scenic_id<>'scenic'\n");
        List<String> scenic_id=new ArrayList<>();
        List<String> scenic_name=new ArrayList<>();
        List<Integer> trash_num=new ArrayList<>();
        List<Integer> rest_num=new ArrayList<>();
        List<Integer> park_num=new ArrayList<>();
        List<Integer> person_num=new ArrayList<>();
        double trash_pa=0.3;
        double rest_pa=0.8;
        double park_pa=0.5;

        while (resultSet1.next()){
            scenic_id.add(resultSet1.getString(1));
            scenic_name.add(resultSet1.getString(2));
        }
        dBconnection1.FreeResource();
        for (int i=0;i<scenic_id.size();i++){
            //Trash
            DBconnection dBconnection=new DBconnection();
            ResultSet resultSet=dBconnection.DB_FindDataSet("select count(*) from trash_detail where scenic_id='"+scenic_id.get(i)+"';\n");
            while (resultSet.next()){
                trash_num.add(resultSet.getInt(1));
            }
            dBconnection.FreeResource();

            //Rest
            DBconnection dBconnection2=new DBconnection();
            ResultSet resultSet2=dBconnection2.DB_FindDataSet("select count(*) from rest_detail where scenic_id='"+scenic_id.get(i)+"';\n");
            while (resultSet2.next()){
                rest_num.add(resultSet2.getInt(1));
            }
            dBconnection2.FreeResource();

            //Park
            DBconnection dBconnection3=new DBconnection();
            ResultSet resultSet3=dBconnection3.DB_FindDataSet("select count(*) from park_detail where scenic_id='"+scenic_id.get(i)+"';\n");
            while (resultSet3.next()){
                park_num.add(resultSet3.getInt(1));
            }
            dBconnection3.FreeResource();

            //游客总人数
            DBconnection dBconnection4=new DBconnection();
            ResultSet resultSet4=dBconnection4.DB_FindDataSet("call get_OldDays_visit_count('"+scenic_id.get(i)+"','"+Day30+"');\n");
            while (resultSet4.next()){
                person_num.add(resultSet4.getInt(1));
            }
            dBconnection4.FreeResource();
        }
        for (int i=0;i<scenic_id.size();i++){
            double trash_value=trash_pa*trash_num.get(i)*person_num.get(i);
            double rest_value=rest_pa*rest_num.get(i)*person_num.get(i);
            double park_value=park_pa*park_num.get(i)*person_num.get(i);
            int value=(int)(trash_value+rest_value+park_value);
            jsonObj.put("scenic_name",scenic_name.get(i));
            jsonObj.put("scenic_value",value);
            jsonArray.add(jsonObj);
        }
        return jsonArray;
    }
}
