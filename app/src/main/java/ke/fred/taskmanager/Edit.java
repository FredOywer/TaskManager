package ke.fred.taskmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import ke.fred.taskmanager.Db.DbHelper;

/**
 * Created by Fredrick on 26/05/2015.
 */
public class Edit extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    Bundle receive = null;
    String ttl, type, bundledTitle = null;
    EditText editTitle;
    ImageButton update;
    RadioGroup pr;
    RadioButton high, low;
    ViewGroup myLayout;
    DbHelper dbHelper;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        toolbar = (Toolbar)findViewById(R.id.appBar2);
        setSupportActionBar(toolbar);

        assert getSupportActionBar()!= null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = (EditText) findViewById(R.id.etEdit);
        update = (ImageButton)findViewById(R.id.ibUpdate);
        high = (RadioButton)findViewById(R.id.radioHigh);
        low = (RadioButton)findViewById(R.id.radioLow);
        pr = (RadioGroup)findViewById(R.id.pr);
        myLayout = (ViewGroup)findViewById(R.id.rLay2);

        update.setOnClickListener(this);
        update.setOnTouchListener(this);
       // pr.setOnCheckedChangeListener(this);
        pr.getCheckedRadioButtonId();


        receive = getIntent().getExtras();
        bundledTitle = receive.getString("SentTitle");
        editTitle.setText(bundledTitle);
        getSupportActionBar().setTitle(bundledTitle);
    }

  /*  @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radioHigh:
                type = "High";
                break;

            case R.id.radioLow:
                type = "Low";
                break;
        }

    } */

    @Override
    public void onClick(View v) {
        ttl = editTitle.getText().toString();
        if (v.getId()== R.id.ibUpdate){
            if (ttl.equals("")){
                Toast.makeText(this, "Fill in the field", Toast.LENGTH_LONG).show();
            }
            /*else if (!high.isChecked() && !low.isChecked()){
                Toast.makeText(this, "Set the priority", Toast.LENGTH_LONG).show();
            } */
            else {
                if (high.isChecked()){
                    type = "High";
                }
                else {
                    type= "Low";
                }

                DateFormat dateFormat = DateFormat.getDateInstance();
                dbHelper = new DbHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(DbHelper.TITLE, ttl);
                cv.put(DbHelper.DATE, dateFormat.format(new Date()));
                cv.put(DbHelper.PRIORITY, type);

                try {
                    db.update(DbHelper.TODO_TABLE, cv, DbHelper.TITLE + "=?", new String[]{bundledTitle});
                } catch (Exception e) {
                    Log.e("TROUBLE UPDATING", e.getMessage());
                }
                db.close();
                this.finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View btn = findViewById(R.id.ibUpdate);
        ViewGroup.LayoutParams btnSize = btn.getLayoutParams();
        btnSize.height = 96;
        btnSize.width = 96;
        btn.setLayoutParams(btnSize);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help){
            startActivity(new Intent(this, Help.class));
        }
        if (id == R.id.about){
            startActivity(new Intent(this, About.class));
        }
       /* if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        } */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }
}
