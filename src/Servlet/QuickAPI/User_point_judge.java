package Servlet.QuickAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 用于支持快应用场景化服务
//判断游客是否位于某个景区/景点/自定义区域
@WebServlet(name = "User_point_judge")
public class User_point_judge extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        double lng = Double.parseDouble(request.getParameter("lng"));
        double lat = Double.parseDouble(request.getParameter("lat"));
        Point[] ps = new Point[] { new Point(122.102866,37.508357), new Point(122.102394,37.507472), new Point(122.102802,37.506723), new Point(122.102136,37.504663), new Point(122.102201,37.503914) ,
                new Point(122.103102,37.503038), new Point(122.103928,37.502927), new Point(122.105237,37.502604), new Point(122.108348,37.503063), new Point(122.109443,37.503063) ,
                new Point(122.111224,37.503165), new Point(122.113563,37.503029), new Point(122.113563,37.503029), new Point(122.116073,37.504646), new Point(122.11721,37.504663) ,
                new Point(122.117253,37.505344), new Point(122.116052,37.505259), new Point(122.114657,37.505855), new Point(122.114614,37.506195), new Point(122.113048,37.506434) ,
                new Point(122.113048,37.507046), new Point(122.109056,37.50754), new Point(122.106567,37.507268), new Point(122.102791,37.508255) };
        Point n1 = new Point(lng,lat);
        printWriter.println(isPtInPoly(n1.getX() , n1.getY() , ps));

    }
    // Point类
    public static class Point {
        private Double x;
        private Double y;
        public Point (Double x , Double y) {
            this.x = x;
            this.y = y;
        }
        public Double getX() {
            return x;
        }
        public void setX(Double x) {
            this.x = x;
        }
        public Double getY() {
            return y;
        }
        public void setY(Double y) {
            this.y = y;
        }

    }
    public static boolean isPtInPoly (double ALon , double ALat , Point[] ps) {
        int iSum, iCount, iIndex;
        double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
        if (ps.length < 3) {
            return false;
        }
        iSum = 0;
        iCount = ps.length;
        for (iIndex = 0; iIndex<iCount;iIndex++) {
            if (iIndex == iCount - 1) {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[0].getX();
                dLat2 = ps[0].getY();
            } else {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[iIndex + 1].getX();
                dLat2 = ps[iIndex + 1].getY();
            }
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat) ) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < ALon) {
                        iSum++;
                    }
                }
            }
        }
        if ((iSum % 2) != 0) {
            return true;
        }
        return false;
    }
}
