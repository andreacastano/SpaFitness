package co.yolima.spafitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private String nombre,correo;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.menu_Perfil){
            Intent intent=new Intent( MainActivity.this, PerfilActivity.class );
            intent.putExtra("nombre",nombre);
            intent.putExtra("correo",correo);
            startActivity( intent );
        }
        if (item.getItemId()== R.id.menu_Ubicacion){
            startActivity( new Intent( MainActivity.this, MiUbicacionActivity.class ) );
        }
        if (item.getItemId()== R.id.menu_Salir){
            mAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Bundle extras=getIntent().getExtras();
        nombre= extras.getString("nombre");
        correo= extras.getString("email");

        // Fragments
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        // Barras de accion.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){

            }
        };

        // Action bar moves
        ActionBar.Tab tab = actionBar.newTab().setText("PEDIR CITAS").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("CANCELAR CITAS").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("MIS RESERVAS").setTabListener(tabListener);
        actionBar.addTab(tab);
    }

    // fragments
    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new PedirCitasFragment();
                case 1: return new CancelarCitasFragment();
                case 2: return new MisReservasFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}