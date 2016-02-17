package es.javiergarciaescobedo.ejemplogooglemapsandroidapi23;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* En el layout de esta Activity se ha añadido un Fragment,
         al que se le ha dado el id: map.
         Ese Fragment se va a asociar a la clase MyMapFragment que está desarrollada en este
         proyecto como un extends SupportMapFragment
         */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyMapFragment myMapFragment = new MyMapFragment();
        fragmentTransaction.add(R.id.map, myMapFragment);  //Aquí se asocia
        fragmentTransaction.commit();
    }

}
