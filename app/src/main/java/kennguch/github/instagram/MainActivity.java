package kennguch.github.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kennguch.github.instagram.fragments.FavoriteFragment;
import kennguch.github.instagram.fragments.HomeFragment;
import kennguch.github.instagram.fragments.ProfileFragment;
import kennguch.github.instagram.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.action_post:
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        case R.id.action_favorite:
                            selectedFragment = new FavoriteFragment();
                            break;
                        case R.id.action_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_fragment, selectedFragment)
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

        mBottomNavigationView = findViewById(R.id.bottom_nav_bar);

        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, new HomeFragment())
                .commit();
    }
}
