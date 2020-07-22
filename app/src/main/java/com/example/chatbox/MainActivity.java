package com.example.chatbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatbox.databinding.ActivityMainBinding;
import com.example.chatbox.menu.CallFragment;
import com.example.chatbox.menu.ChatFragment;
import com.example.chatbox.menu.StatusFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setUpWithViewPager(binding.ViewPager);
        binding.TabLayout.setupWithViewPager(binding.ViewPager);
        setSupportActionBar(binding.MainActivityToolbar);
        binding.ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeFabIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.SearchMenu)
        {
            Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
        }

        else if(item.getItemId() == R.id.MoreMenu)
        {
            Toast.makeText(this,"More",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpWithViewPager(ViewPager viewPager)
    {
        MainActivity.SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatFragment(),"Chat");
        adapter.addFragment(new StatusFragment(),"Status");
        adapter.addFragment(new CallFragment(),"Call");
        viewPager.setAdapter(adapter);
    }

    //Some function

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void changeFabIcon(final int index)
    {
        binding.FloatingActionButton.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch(index)
                {
                    case 0 : binding.FloatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_24));break;
                    case 1 : binding.FloatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_24));break;
                    case 2 : binding.FloatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_call_24));break;
                }
                binding.FloatingActionButton.show();
            }
        },100);

    }

}