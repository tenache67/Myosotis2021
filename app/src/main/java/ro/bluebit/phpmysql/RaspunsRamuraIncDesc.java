package ro.bluebit.phpmysql;

public class RaspunsRamuraIncDesc {

    private String id_antet_trimiteri;
    private String id_p_lucru;
    private String dataora;
    private String id_expeditor;
    private String id_destinatar;
    private String id_tip;

    public RaspunsRamuraIncDesc(String id_antet_trimiteri, String id_p_lucru, String dataora, String id_expeditor, String id_destinatar, String id_tip) {
        this.id_antet_trimiteri = id_antet_trimiteri;
        this.id_p_lucru = id_p_lucru;
        this.dataora = dataora;
        this.id_expeditor = id_expeditor;
        this.id_destinatar = id_destinatar;
        this.id_tip = id_tip;
    }

    public String getId_antet_trimiteri() {
        return id_antet_trimiteri;
    }

    public void setId_antet_trimiteri(String id_antet_trimiteri) {
        this.id_antet_trimiteri = id_antet_trimiteri;
    }

    public String getId_p_lucru() {
        return id_p_lucru;
    }

    public void setId_p_lucru(String id_p_lucru) {
        this.id_p_lucru = id_p_lucru;
    }

    public String getDataora() {
        return dataora;
    }

    public void setDataora(String dataora) {
        this.dataora = dataora;
    }

    public String getId_expeditor() {
        return id_expeditor;
    }

    public void setId_expeditor(String id_expeditor) {
        this.id_expeditor = id_expeditor;
    }

    public String getId_destinatar() {
        return id_destinatar;
    }

    public void setId_destinatar(String id_destinatar) {
        this.id_destinatar = id_destinatar;
    }

    public String getId_tip() {
        return id_tip;
    }

    public void setId_tip(String id_tip) {
        this.id_tip = id_tip;
    }
}

