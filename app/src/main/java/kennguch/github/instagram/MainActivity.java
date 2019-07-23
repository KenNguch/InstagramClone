package kennguch.github.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import kennguch.github.instagram.fragments.FavouriteFragments;
import kennguch.github.instagram.fragments.HomeFragment;
import kennguch.github.instagram.fragments.ProfilerFragment;
import kennguch.github.instagram.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mButtomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            selectFragment = new HomeFragment();
                            break;
                        case R.id.action_search:
                            selectFragment = new SearchFragment();
                            break;
                        case R.id.action_post:
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            finish();
                            break;
                        case R.id.action_favourite:
                            selectFragment = new FavouriteFragments();
                            break;
                        case R.id.action_profile:
                            selectFragment = new ProfilerFragment();
                            break;
                    }
                    if (selectFragment != null) {

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_fragment, selectFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                    return false;
                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButtomNavigationView = findViewById(R.id.bottom_nav_bar);

        mButtomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).commit();
    }
}


