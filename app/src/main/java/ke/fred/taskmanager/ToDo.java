package ke.fred.taskmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.util.Date;

import ke.fred.taskmanager.Db.DbHelper;

/**
 * Created by Fredrick on 04/06/2015.
 */
public class ToDo extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    String task, type = "";
    ImageButton save;
    TextInputLayout inputTask;
    EditText desc;
    RadioButton high, low;
    RadioGroup pr;
    ViewGroup myLayout;
    DbHelper dbHelper;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);

        toolbar = (Toolbar)findViewById(R.id.appBar2);
        setSupportActionBar(toolbar);
        assert getSupportActionBar()!= null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save = (ImageButton)findViewById(R.id.ibTick);
        inputTask = (TextInputLayout)findViewById(R.id.txtInput);
        desc = (EditText)findViewById(R.id.etDesc);
        high = (RadioButton)findViewById(R.id.radioHigh);
        low = (RadioButton)findViewById(R.id.radioLow);
        pr = (RadioGroup)findViewById(R.id.pr);
        myLayout = (ViewGroup)findViewById(R.id.rlay1);

        dbHelper = new DbHelper(this);
        save.setOnClickListener(this);
        save.setOnTouchListener(this);
        pr.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radioHigh:
                type = "High";
                break;

            case R.id.radioLow:
                type = "Low";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ibTick){
            task = desc.getText().toString();

            if (task.equals("")){
                inputTask.setError("Please insert the title of the task");
            }
            else {
                DateFormat dateFormat = DateFormat.getDateInstance();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(DbHelper.TITLE, task);
                cv.put(DbHelper.DATE, dateFormat.format(new Date()));
                cv.put(DbHelper.PRIORITY, type);
                db.insert(DbHelper.TODO_TABLE, null, cv);

                Holder values = new Holder();
                values.setTitle(task);
                values.setDate(dateFormat.format(new Date()));

                db.close();
                this.finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View btn = findViewById(R.id.ibTick);

       /* RelativeLayout.LayoutParams btnPosition = new RelativeLayout.LayoutParams(48, 48);
        btnPosition.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        btn.setLayoutParams(btnPosition); */

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