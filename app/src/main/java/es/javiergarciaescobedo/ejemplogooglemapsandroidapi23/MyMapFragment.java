package es.javiergarciaescobedo.ejemplogooglemapsandroidapi23;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
Se debe obtener una clave para poder usar los mapas de Google. Esta clave (Key) se obtiene
accediendo a la Google Developer Console en:
    https://console.developers.google.com/project
En esa consola de debe crear un proyecto y habilitar la API: Google Maps Android API.
Para ello necesitarás un certificado SHA de desarrollador que puedes obtener accediendo a la
carpeta .android que debes tener dentro de tu carpeta personal del PC, y ahí ejecuta:
    keytool -list -alias androiddebugkey -keystore debug.keystore -storepass android -keypass android
Aparecerá un código hexadecimal largo que deberás indicar para activar la API de Google Maps
en Google Developer Console, junto con el nombre del paquete de la aplicación que puedes ver en
el archivo Manifest de tu aplicación. Por ejemplo:
    es.javiergarciaescobedo.ejemplogooglemapsandroidapi23   E5:9F:6F:21:53:9F:C6:3F:BF:BF:BB:4F:0F:7B:55:46:67:57:B4:7E
Obtendrás una clave del tipo: AIzaSyCqn8jdRqWI_BOkdnmdHkn2xHk1hmg5gpc
Esa clave la debes indicar en el archivo AndroidManifest como puedes ver en ese mismo archivo de este proyecto

En el proyecto se debe indicar que hay una dependencia con la librería gms:play-services-maps.
Esto puedes hacerlo desde Android Studio usando el menú 'File > Project Structure > app > Dependencies'
y usando el botón '+' agregar una 'Library dependency' seleccionando la que indique:
com.google.android.gms:play-services-maps
Podrás ver en el archivo build.gradle del módulo app que se ha agregado esa dependencia
*/

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap map;
    private final int REQUEST_LOCATION = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // Obtener el mapa de manera asíncrona.
        /* Cuando el mapa está descargado se ejecutará automáticamente el método onMapReady que esté
        declarado más adelante. Esto se produce así porque esta clase se ha declarado
        como implements OnMapReadyCallback como puede verse en el encabezado de la clase
        */
        this.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Se guarda la referencia al objeto map para usarla posteriormente en el método onRequestPermissionsResult
        this.map = map;

        // Posicionar el mapa en una localización y con un nivel de zoom
        LatLng iesLosRemedios = new LatLng(36.6795813,-5.447004);
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.
        float zoom = 13;

        // Mostrar el mapa centrado en la posicion indicada
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(iesLosRemedios, zoom));
        map.addMarker(new MarkerOptions()
                .title("IES Ntra. Sra. de los Remedios")
                .snippet("Visítanos en www.ieslosremedios.org")
                .position(iesLosRemedios));


        // Más opciones para personalizar el marcador en:
        // https://developers.google.com/maps/documentation/android/marker
        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = map.getUiSettings();

        // La aplicación debe conocer si el usuario le ha concedido permisos para el uso de la
        // posición aproximada y de la posición GPS (Sólo se usaría a partir de Android 6, que solicita
        // permisos durante ejecución de la aplicación)
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
        /* La ejecución del método requestPermissions conlleva la ejecución del método onRequestPermissionsResult
          que está implementado en esta misma clase, para comprobar los permisos que ha autorizado el usuario */

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        // Comprobar si la respuesta a la solicitud de permisos corresponde a la petición de conocer
        //  la posición. Se usa el código REQUEST_LOCATION que es una constante declarada en esta clase
        if (requestCode == REQUEST_LOCATION) {
            // Comprobar si el usuario ha autorizado los 2 permisos solictados
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // La aplicación dispone de permisos para conocer la posición
                /* La ejecución de setMyLocationEnabled requiere preguntar de nuevo por los permisos */
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
            } else {
                // El usuario ha rechazado autorizar los permisos
                Toast.makeText(getContext(), "La aplicación no tiene permisos para conocer su ubicación", Toast.LENGTH_LONG).show();
            }
        }
    }

}
