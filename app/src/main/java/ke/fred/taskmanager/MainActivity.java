package ke.fred.taskmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ke.fred.taskmanager.Db.DbHelper;

/**
 * Created by Fredrick on 02/12/2015.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Bundle send;
    ListView lv = null;
    DbHelper dbHelper;
    CustomAdapter adapter = null;
    ArrayList<Holder> tasks;
    FloatingActionButton create;

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Boolean drawerSeen = false;
    private static final String FIRST_TIME = "first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        toolbar = (Toolbar) findViewById(R.id.main_appBar);
        setSupportActionBar(toolbar);

        NavigationView navDrawer = (NavigationView) findViewById(R.id.main_drawer);
        navDrawer.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (!didUserSeeDrawer()){
            showDrawer();
            setDrawerSeen();
        } else {
            hideDrawer();
        }

        create = (FloatingActionButton) findViewById(R.id.fab);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                startActivity(new Intent(getApplicationContext(), ToDo.class));
            }
        });

        dbHelper.openDb();
        display();
    }

    private void display() {
        lv = (ListView) findViewById(R.id.listView);
        tasks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String columns[] = new String[]{DbHelper.ID, DbHelper.TITLE, DbHelper.DATE, DbHelper.PRIORITY};
        Cursor c = db.query(DbHelper.TODO_TABLE, columns, null, null, null, null, null);
        if (c.getCount()>0){
            c.moveToLast();
            do {
                String id = c.getString(c.getColumnIndex(DbHelper.ID));
                String ttl = c.getString(c.getColumnIndex(DbHelper.TITLE));
                String dt = c.getString(c.getColumnIndex(DbHelper.DATE));
                String pri = c.getString(c.getColumnIndex(DbHelper.PRIORITY));

                Holder values = new Holder();
                values.setId(id);
                values.setTitle(ttl);
                values.setDate(dt);
                values.setPriority(pri);
                tasks.add(values);

            } while (c.moveToPrevious());

            adapter = new CustomAdapter(this, R.layout.item_layout, tasks);
            lv.setAdapter(adapter);
            c.close();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Holder hol = tasks.get(position);
                send = new Bundle();
                String selected = hol.getTitle();
                Intent i = new Intent(getApplicationContext(), Edit.class);
                i.putExtra("SentTitle", selected);
                //finish();
                startActivity(i);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("TASK");
                //alert.setMessage("Choose an option");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Holder sel = tasks.get(position);
                        String selected = sel.getId();
                        tasks.remove(position);
                        adapter.notifyDataSetChanged();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(DbHelper.TODO_TABLE, DbHelper.ID + "=?", new String[]{selected});
                        db.close();
                    }
                });
                alert.setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Holder hol = tasks.get(position);
                        send = new Bundle();
                        String selected = hol.getTitle();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, selected);
                        startActivity(Intent.createChooser(intent, "Share via..."));
                    }
                });
                alert.setCancelable(true);
                alert.show();
                return true;
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_about){
            drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, About.class));
            return true;
        }
        if (menuItem.getItemId() == R.id.nav_help){
            drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, Help.class));
            return true;
        }
        return false;
    }

    private boolean didUserSeeDrawer(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        drawerSeen = prefs.getBoolean(FIRST_TIME, false);
        return drawerSeen;
    }

    private void setDrawerSeen(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        drawerSeen = true;
        prefs.edit().putBoolean(FIRST_TIME, drawerSeen).apply();
    }

    public void showDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void hideDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            startActivity(new Intent(this, Help.class));
        }
        if (id == R.id.about) {
            startActivity(new Intent(this, About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
