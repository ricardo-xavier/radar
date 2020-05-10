package radarws;

public class Distancia {
	
	private static final double _eQuatorialEarthRadius = 6378.1370D;
    private static final double _d2r = (Math.PI / 180D);
    
    public static int haversineInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * haversineInKM(lat1, long1, lat2, long2));
    }

    public static double haversineInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;
        return d;
    }
    
    public static void main(String[] args) {
    	double latCasa = -19.9735106;
    	double longCasa = -43.9667602;
    	double latBarroca = -19.9369742;
    	double longBarroca = -43.9592253;
    	int distCasaBarroca = haversineInM(latCasa, longCasa, latBarroca, longBarroca);
    	System.out.println(distCasaBarroca);
    	
    	double latBrasilia = -15.7213868;
    	double longBrasilia = -48.0777897;
    	int distCasaBrasilia = haversineInM(latCasa, longCasa, latBrasilia, longBrasilia);
    	System.out.println(distCasaBrasilia);
    	
	}
}
