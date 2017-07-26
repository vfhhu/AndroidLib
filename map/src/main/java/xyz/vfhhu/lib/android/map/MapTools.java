package xyz.vfhhu.lib.android.map;

/**
 * Created by Dexter on 2016/8/20.
 */
public class MapTools {
    //帶入使用者及景點店家經緯度可計算出距離
    public static double distance(double latitudeFrom,double longitudeFrom, double latitudeTo,double longitudeTo) {
        double radLatitude1 = latitudeFrom * Math.PI / 180;
        double radLatitude2 = latitudeTo * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitudeFrom * Math.PI / 180 - longitudeTo * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;

        //單位是公尺
        return distance;
    }
    public static double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
        double d = 0;
        lat_a = lat_a * Math.PI / 180;
        lng_a = lng_a * Math.PI / 180;
        lat_b = lat_b * Math.PI / 180;
        lng_b = lng_b * Math.PI / 180;

        d = Math.sin(lat_a) * Math.sin(lat_b) + Math.cos(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
        d = Math.sqrt(1 - d * d);
        d = Math.cos(lat_b) * Math.sin(lng_b - lng_a) / d;
        if(d>1)d=1;
        if(d<-1)d=-1;
        d = Math.asin(d) * 180 / Math.PI;

        if (lat_b >= lat_a) {
            if (d < 0) {
                d = 360 + d;
            }
        } else {
            d = 180 - d;
        }

        return d;
    }


    public static double distanceTWD97(double[] p1,double[] p2){
        return Math.pow(Math.pow((p2[0]-p1[0]),2)+Math.pow((p2[1]-p1[1]),2),0.5);
    }
    public static double distance_to_line_TWD97(double[] pp,double[] p1,double[] p2){
        double s=Math.abs((pp[0]*p1[1]+p1[0]*p2[1]+p2[0]*pp[1])-(p1[0]*pp[1]+p2[0]*p1[1]+pp[0]*p2[1]))/2;
        double length=Math.pow((Math.pow((p2[0]-p1[0]),2)+Math.pow((p2[1]-p1[1]),2)),(0.5));
        double h=s/length*2;
        return h;
    }


//    public static double[] TWD97ToLatLon_O( double x, double y) {
//        double a = 6378137.0;
//        double b = 6356752.314245;
//        double lon0 = Math.toRadians(121);
//        double k0 = 0.9999;
//        double dx = 250000;
//        double dy = 0;
//        double e = Math.pow((1-Math.pow(b,2)/Math.pow(a,2)),0.5);
//
//        x -= dx;
//        y -= dy;
//
//        // Calculate the Meridional Arc
//        double M = y/k0;
//
//        // Calculate Footprint Latitude
//        double mu = M/(a*(1.0 - Math.pow(e, 2)/4.0 - 3*Math.pow(e, 4)/64.0 - 5*Math.pow(e, 6)/256.0));
//        double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));
//
//        double J1 = (3*e1/2 - 27*Math.pow(e1, 3)/32.0);
//        double J2 = (21*Math.pow(e1, 2)/16 - 55*Math.pow(e1, 4)/32.0);
//        double J3 = (151*Math.pow(e1, 3)/96.0);
//        double J4 = (1097*Math.pow(e1, 4)/512.0);
//
//        double fp = mu + J1*Math.sin(2*mu) + J2*Math.sin(4*mu) + J3*Math.sin(6*mu) + J4*Math.sin(8*mu);
//
//        // Calculate Latitude and Longitude
//
//        double e2 = Math.pow((e*a/b), 2);
//        double C1 = Math.pow(e2*Math.cos(fp), 2);
//        double T1 = Math.pow(Math.tan(fp), 2);
//        double R1 = a*(1-Math.pow(e, 2))/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), (3.0/2.0));
//        double N1 = a/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), 0.5);
//
//        double D = x/(N1*k0);
//
//        // lat
//        double Q1 = N1*Math.tan(fp)/R1;
//        double Q2 = (Math.pow(D, 2)/2.0);
//        double Q3 = (5 + 3*T1 + 10*C1 - 4*Math.pow(C1, 2) - 9*e2)*Math.pow(D, 4)/24.0;
//        double Q4 = (61 + 90*T1 + 298*C1 + 45*Math.pow(T1, 2) - 3*Math.pow(C1, 2) - 252*e2)*Math.pow(D, 6)/720.0;
//        double lat = fp - Q1*(Q2 - Q3 + Q4);
//
//        // long
//        double Q5 = D;
//        double Q6 = (1 + 2*T1 + C1)*Math.pow(D, 3)/6;
//        double Q7 = (5 - 2*C1 + 28*T1 - 3*Math.pow(C1, 2) + 8*e2 + 24*Math.pow(T1, 2))*Math.pow(D, 5)/120.0;
//        double lon = lon0 + (Q5 - Q6 + Q7)/Math.cos(fp);
//
//        return new double[] {Math.toDegrees(lat), Math.toDegrees(lon)};
//    }
//
//
//
//    public static double[] LatLonToTWD97_O(double lat, double lon){
//        lat=Math.toRadians(lat);
//        lon=Math.toRadians(lon);
//        double a = 6378137.0;
//        double b = 6356752.314245;
//        double lon0 = Math.toRadians(121);
//        double k0 = 0.9999;
//        double dx = 250000;
//
//        double e = Math.pow((1 - Math.pow(b,2) / Math.pow(a,2)), 0.5);
//        double e2 = Math.pow(e,2)/(1-Math.pow(e,2));
//        double n = ( a - b ) / ( a + b );
//        double nu = a / Math.pow((1-(Math.pow(e,2)) * (Math.pow(Math.sin(lat), 2) ) ) , 0.5);
//        double p = lon - lon0;
//
//        double pown2=Math.pow(n,2);
//        double pown3=Math.pow(n,3);
//        double pown4=Math.pow(n,4);
//        double pown5=Math.pow(n,5);
//
//        double A = a * (1 - n + (5/4) * (pown2 - pown3) + (81/64) * (pown4  - pown5));
//        double B = (3 * a * n/2.0) * (1 - n + (7/8.0)*(pown2 - pown3) + (55/64.0)*(pown4 - pown5));
//        double C = (15 * a * (pown2)/16.0)*(1 - n + (3/4.0)*(pown2 - pown3));
//        double D = (35 * a * (pown3)/48.0)*(1 - n + (11/16.0)*(pown2 - pown3));
//        double E = (315 * a * (pown4)/51.0)*(1 - n);
//
//
//        double S = A * lat - B * Math.sin(2 * lat) +C * Math.sin(4 * lat) - D * Math.sin(6 * lat) + E * Math.sin(8 * lat);
//
//        //計算Y值
//        double K1 = S*k0;
//        double K2 = k0*nu*Math.sin(2*lat)/4.0;
//        double K3 = (k0*nu*Math.sin(lat)*(Math.pow(Math.cos(lat),3))/24.0) * (5 - Math.pow(Math.tan(lat),2) + 9*e2*Math.pow((Math.cos(lat)),2) + 4*(Math.pow(e2,2))*(Math.pow(Math.cos(lat),4)));
//        double y = K1 + K2*(Math.pow(p,2)) + K3*(Math.pow(p,4));
//
//        //計算X值
//        double K4 = k0*nu*Math.cos(lat);
//        double K5 = (k0*nu*(Math.pow(Math.cos(lat),3))/6.0) * (1 - Math.pow(Math.tan(lat),2) + e2*(Math.pow(Math.cos(lat),2)));
//        double x = K4 * p + K5 * (Math.pow(p, 3)) + dx;
//
//        double[] ret={x,y};
//        return ret;
//    }


    public static double[] TWD97ToLatLon( double x, double y) {
        double a = TMParameter.a;
        double b = TMParameter.b;
        double lon0 = TMParameter.lon0;
        double k0 = TMParameter.k0;
        double dx = TMParameter.dx;
        double dy = TMParameter.dy;
        double e = TMParameter.e;

        x -= dx;
        y -= dy;

        // Calculate the Meridional Arc
        double M = y/k0;

        // Calculate Footprint Latitude
        double mu = M/(a*(1.0 - Math.pow(e, 2)/4.0 - 3*Math.pow(e, 4)/64.0 - 5*Math.pow(e, 6)/256.0));
        //double e1 = TMParameter.e1;

        double J1 = TMParameter.J1;
        double J2 = TMParameter.J2;
        double J3 = TMParameter.J3;
        double J4 = TMParameter.J4;

        double fp = mu + J1*Math.sin(2*mu) + J2*Math.sin(4*mu) + J3*Math.sin(6*mu) + J4*Math.sin(8*mu);

        // Calculate Latitude and Longitude

        double e2 = Math.pow((e*a/b), 2);
        double C1 = Math.pow(e2*Math.cos(fp), 2);
        double T1 = Math.pow(Math.tan(fp), 2);
        double R1 = a*(1-Math.pow(e, 2))/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), (3.0/2.0));
        double N1 = a/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), 0.5);

        double D = x/(N1*k0);

        // lat
        double Q1 = N1*Math.tan(fp)/R1;
        double Q2 = (Math.pow(D, 2)/2.0);
        double Q3 = (5 + 3*T1 + 10*C1 - 4*Math.pow(C1, 2) - 9*e2)*Math.pow(D, 4)/24.0;
        double Q4 = (61 + 90*T1 + 298*C1 + 45*Math.pow(T1, 2) - 3*Math.pow(C1, 2) - 252*e2)*Math.pow(D, 6)/720.0;
        double lat = fp - Q1*(Q2 - Q3 + Q4);

        // long
        double Q5 = D;
        double Q6 = (1 + 2*T1 + C1)*Math.pow(D, 3)/6;
        double Q7 = (5 - 2*C1 + 28*T1 - 3*Math.pow(C1, 2) + 8*e2 + 24*Math.pow(T1, 2))*Math.pow(D, 5)/120.0;
        double lon = lon0 + (Q5 - Q6 + Q7)/Math.cos(fp);

        return new double[] {Math.toDegrees(lat), Math.toDegrees(lon)};
    }



    public static double[] LatLonToTWD97(double lat, double lon){
        lat=Math.toRadians(lat);
        lon=Math.toRadians(lon);
        double a = TMParameter.a;
        double b = TMParameter.b;
        double lon0 = TMParameter.lon0;
        double k0 = TMParameter.k0;
        double dx = TMParameter.dx;

        double e = TMParameter.e;
        double e2 = Math.pow(e,2)/(1-Math.pow(e,2));
        double n = TMParameter.n;
        double nu = a / Math.pow((1-(Math.pow(e,2)) * (Math.pow(Math.sin(lat), 2) ) ) , 0.5);
        double p = lon - lon0;


        double A = TMParameter.A;
        double B = TMParameter.B;
        double C = TMParameter.C;
        double D = TMParameter.D;
        double E = TMParameter.E;


        double S = A * lat - B * Math.sin(2 * lat) +C * Math.sin(4 * lat) - D * Math.sin(6 * lat) + E * Math.sin(8 * lat);

        //計算Y值
        double K1 = S*k0;
        double K2 = k0*nu*Math.sin(2*lat)/4.0;
        double K3 = (k0*nu*Math.sin(lat)*(Math.pow(Math.cos(lat),3))/24.0) * (5 - Math.pow(Math.tan(lat),2) + 9*e2*Math.pow((Math.cos(lat)),2) + 4*(Math.pow(e2,2))*(Math.pow(Math.cos(lat),4)));
        double y = K1 + K2*(Math.pow(p,2)) + K3*(Math.pow(p,4));

        //計算X值
        double K4 = k0*nu*Math.cos(lat);
        double K5 = (k0*nu*(Math.pow(Math.cos(lat),3))/6.0) * (1 - Math.pow(Math.tan(lat),2) + e2*(Math.pow(Math.cos(lat),2)));
        double x = K4 * p + K5 * (Math.pow(p, 3)) + dx;

        double[] ret={x,y};
        return ret;
    }

}

class TMParameter{
    //all
    final public static double a = 6378137.0;
    final public static double b = 6356752.314245;
    final public static double lon0 = Math.toRadians(121);
    final public static double k0 = 0.9999;
    final public static double dx = 250000;
    final public static double dy = 0;
    final public static double e = Math.pow((1-Math.pow(b,2)/Math.pow(a,2)),0.5);


    //LatLonToTWD97
    final public static double n = ( a - b ) / ( a + b );

    final public static double A = a * (1 - n + (5/4) * (Math.pow(n,2) - Math.pow(n,3)) + (81/64) * (Math.pow(n, 4)  - Math.pow(n, 5)));
    final public static double B = (3 * a * n/2.0) * (1 - n + (7/8.0)*(Math.pow(n,2) - Math.pow(n,3)) + (55/64.0)*(Math.pow(n,4) - Math.pow(n,5)));
    final public static double C = (15 * a * (Math.pow(n,2))/16.0)*(1 - n + (3/4.0)*(Math.pow(n,2) - Math.pow(n,3)));
    final public static double D = (35 * a * (Math.pow(n,3))/48.0)*(1 - n + (11/16.0)*(Math.pow(n,2) - Math.pow(n,3)));
    final public static double E = (315 * a * (Math.pow(n,4))/51.0)*(1 - n);


    //TWD97ToLatLon
    final public static double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));
    final public static double J1 = (3*e1/2 - 27*Math.pow(e1, 3)/32.0);
    final public static double J2 = (21*Math.pow(e1, 2)/16 - 55*Math.pow(e1, 4)/32.0);
    final public static double J3 = (151*Math.pow(e1, 3)/96.0);
    final public static double J4 = (1097*Math.pow(e1, 4)/512.0);

}
