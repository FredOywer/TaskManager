package ke.fred.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Fredrick on 20/11/2015.
 */
public class Help extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager vPager;
   // private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appBar2);
        setSupportActionBar(toolbar);
        assert getSupportActionBar()!= null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        vPager = (ViewPager)findViewById(R.id.vPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);

        tabLayout.setupWithViewPager(vPager);
        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about){
            startActivity(new Intent(this, About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* public static class MyFragment extends Fragment{
        public static final String PAGE = "arg_page";

        public MyFragment(){

        }

        public static MyFragment newInstance(int pageNumber){
            MyFragment fragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(PAGE, pageNumber);
            fragment.setArguments(arguments);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Bundle args = getArguments();
            int pageNumber = args.getInt(PAGE);
            TextView tv = new TextView(getActivity());
            tv.setText(R.string.frag_text + pageNumber);
            tv.setGravity(Gravity.CENTER);
            return tv;
        }
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Help.MyFragment frag = Help.MyFragment.newInstance(position);
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + position;
        }
    } */
}
