package ro.bluebit.UTILITARE;

public class ClasaIteme {
    private int mResurseImagini;
    private String mTextLinie;

    public ClasaIteme(int ResurseImagini, String TextLinie){
        mResurseImagini=ResurseImagini;
        mTextLinie=TextLinie;
    }
    public int getResurseImagini(){
        return mResurseImagini;
    }
    public String getTextLinie(){
        return mTextLinie;
    }
}
