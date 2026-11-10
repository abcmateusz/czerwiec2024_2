package com.example.muzyka04;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String[]> albumy = new ArrayList<>();
    int numer = 0;

    TextView nazwaWyk, tytAlb, liczUtw, rokWyd, iloPob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nazwaWyk = findViewById(R.id.nazwaWyk);
        tytAlb = findViewById(R.id.tytAlb);
        liczUtw = findViewById(R.id.liczUtw);
        rokWyd = findViewById(R.id.rokWyd);
        iloPob = findViewById(R.id.iloPob);

        ImageButton lewo = findViewById(R.id.lewo);
        ImageButton prawo = findViewById(R.id.prawo);
        Button pobierz = findViewById(R.id.button);

        wczytajDane();
        wyswietlAlbum();

        lewo.setOnClickListener(v -> {
            numer = (numer - 1 + albumy.size()) % albumy.size();
            wyswietlAlbum();
        });

        prawo.setOnClickListener(v -> {
            numer = (numer + 1) % albumy.size();
            wyswietlAlbum();
        });

        pobierz.setOnClickListener(v -> {
            int pobrania = Integer.parseInt(albumy.get(numer)[4]);
            albumy.get(numer)[4] = String.valueOf(pobrania + 1);
            wyswietlAlbum();
        });
    }

    void wczytajDane() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getAssets().open("Data.txt"))
            );

            ArrayList<String> dane = new ArrayList<>();
            String linia;

            while ((linia = br.readLine()) != null) {
                if (!linia.trim().isEmpty()) {
                    dane.add(linia.trim());
                }
            }

            br.close();

            for (int i = 0; i < dane.size(); i += 5) {
                albumy.add(new String[]{
                        dane.get(i),
                        dane.get(i + 1).replace("\"", ""),
                        dane.get(i + 2),
                        dane.get(i + 3),
                        dane.get(i + 4)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void wyswietlAlbum() {
        if (albumy.isEmpty()) return;

        String[] album = albumy.get(numer);

        nazwaWyk.setText(album[0]);
        tytAlb.setText("\"" + album[1] + "\"");
        liczUtw.setText("Liczba utworów: " + album[2]);
        rokWyd.setText("Rok wydania: " + album[3]);
        iloPob.setText("Liczba pobrań: " + album[4]);
    }
}
