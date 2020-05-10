package xavier.ricardo.radar;

public class Individuo {

    private String id;
    private String status;
    private double latitude;
    private double longitude;
    private long atualizacao;
    private int distancia;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public long getAtualizacao() {
        return atualizacao;
    }
    public void setAtualizacao(long atualizacao) {
        this.atualizacao = atualizacao;
    }
    public int getDistancia() {
        return distancia;
    }
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
