package Servlet.QuickAPI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Scenic_Search_API")
public class Scenic_Search_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        String in_put=request.getParameter("input");
        //模拟三个景区
        String[] strArray={"环翠楼公园/环翠楼","环翠楼公园/极目亭","环翠楼公园/孔庙","环翠楼公园/盆景园",
                "云龙湖旅游景区/松石园","云龙湖旅游景区/生态岛","云龙湖旅游景区/紫薇岛","云龙湖旅游景区/芦苇风情园","云龙湖旅游景区/望湖阁",
                "岱湖公园/绿城古镇","岱湖公园/儿童乐园",
                "龙霄公园/千里足道","龙霄公园/园林管理处"};
        //三个景区的景区名称
        String[] str_ScenicName={"环翠楼公园","环翠楼公园","环翠楼公园","环翠楼公园",
                "云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区",
                "岱湖公园","岱湖公园",
                "龙霄公园","龙霄公园"};
        //三个景区内的景点名称
        String[] str_ScenicSpots={"环翠楼","极目亭","孔庙","盆景园",
                "松石园","生态岛","紫薇岛","芦苇风情园","望湖阁",
                "绿城古镇","儿童乐园",
                "千里足道","园林管理处"};

//        //模拟三个景区
//        String[] strArray={"环翠楼公园/环翠楼","环翠楼公园/极目亭","环翠楼公园/孔庙","环翠楼公园/盆景园",
//                "云龙湖旅游景区/松石园","云龙湖旅游景区/生态岛","云龙湖旅游景区/紫薇岛","云龙湖旅游景区/芦苇风情园","云龙湖旅游景区/望湖阁",
//                "岱湖公园/绿城古镇","岱湖公园/儿童乐园"};
//        //三个景区的景区名称
//        String[] str_ScenicName={"环翠楼公园","环翠楼公园","环翠楼公园","环翠楼公园",
//                "云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区","云龙湖旅游景区",
//                "岱湖公园","岱湖公园"};
//        //三个景区内的景点名称
//        String[] str_ScenicSpots={"环翠楼/极目亭/孔庙/盆景园","环翠楼/极目亭/孔庙/盆景园","环翠楼/极目亭/孔庙/盆景园","环翠楼/极目亭/孔庙/盆景园",
//                "松石园/生态岛/紫薇岛/芦苇风情园/望湖阁","松石园/生态岛/紫薇岛/芦苇风情园/望湖阁","松石园/生态岛/紫薇岛/芦苇风情园/望湖阁","松石园/生态岛/紫薇岛/芦苇风情园/望湖阁","松石园/生态岛/紫薇岛/芦苇风情园/望湖阁",
//                "绿城古镇/儿童乐园","绿城古镇/儿童乐园"};
        double[] lng={122.113042,122.108343,122.103418,122.115435,117.129265,117.137419,117.135917,117.141925,117.140531,116.917662,116.91734,116.943998,116.945972};
        double[] lat={37.504612,37.504868,37.505178,37.505008,34.237274,34.238214,34.232076,34.234258,34.231916,34.215453,34.21194,34.172853,34.172698};
        String[] scenic_id={"huancui_Building","huancui_JiMuTing","huancui_KongMiao","huancui_PenJing",
                "yunlong_Songshi","yunliong_Shengtai","yunlong_Ziwei","yunlong_Luwei","yunlong_Wanghu",
                "Daihu_LuCheng","Daihu_Child","QianLi","Manager"};
        int[] index={0,0,0,0,0,0,0,0,0,0,0,0,0};

        for(int i=0;i<strArray.length;i++){
            if(contain_str(in_put,strArray[i])){
                index[i]=1;
            }else {
                index[i]=0;
            }
        }
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<index.length;i++){
            if(index[i]==1){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("scenic_name",str_ScenicName[i]);
                jsonObject.put("scenic_spots",str_ScenicSpots[i]);
                jsonObject.put("lng",lng[i]);
                jsonObject.put("lat",lat[i]);
                jsonObject.put("scenic_id",scenic_id[i]);
                jsonArray.add(jsonObject);
            }
        }
        JSONArray latest = new JSONArray();
        latest.add(jsonArray.getJSONObject(0));
        for(int i=1;i<jsonArray.size();i++){
            if(!exist_object(latest,jsonArray.getJSONObject(i))){
                latest.add(jsonArray.getJSONObject(i));
            }
        }
        printWriter.println(jsonArray);
    }

    public boolean contain_str(String str_in,String str_origin) {
        boolean status = str_origin.contains(str_in);
        if(status){
            return status;
        }else{
            return status;
        }
    }
    public boolean exist_object(JSONArray jsonArray,JSONObject jsonObject){
        for(int i=0;i<jsonArray.size();i++){
            if(jsonObject.getString("scenic_name").equals(jsonArray.getJSONObject(i).getString("scenic_name"))&&
                    jsonObject.getString("scenic_spots").equals(jsonArray.getJSONObject(i).getString("scenic_spots"))&&
                    jsonObject.getString("lng").equals(jsonArray.getJSONObject(i).getString("lng"))&&
                    jsonObject.getString("lat").equals(jsonArray.getJSONObject(i).getString("lat"))&&
                    jsonObject.getString("scenic_id").equals(jsonArray.getJSONObject(i).getString("scenic_id"))){
                return true;
            }
        }
        return false;
    }
}
