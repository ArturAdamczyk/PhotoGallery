package art.com.photogallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import art.com.photogallery.Params.Params;
import art.com.photogallery.R;
import art.com.photogallery.adapters.GalleryPagerAdapter;
import art.com.photogallery.fragments.GalleryFragment;
import art.com.photogallery.fragments.SettingsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements SettingsFragment.Callback {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private GalleryPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        adapterViewPager = new GalleryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
    }

    @Override
    public void onFilterOptionChanged(String filterOption, String filterConstraint){
        GalleryFragment galleryFragment = (GalleryFragment) adapterViewPager.getFragment(Params.GALLERY_FRAGMENT);
        if (galleryFragment != null) {
            galleryFragment.setFilterOption(filterOption, filterConstraint);
        }
    }

    @Override
    public void onSortOptionChanged(String sortOption){
        GalleryFragment galleryFragment = (GalleryFragment) adapterViewPager.getFragment(Params.GALLERY_FRAGMENT);
        if (galleryFragment != null) {
            galleryFragment.setSortOption(sortOption);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GalleryFragment galleryFragment = (GalleryFragment) adapterViewPager.getFragment(Params.GALLERY_FRAGMENT);
        galleryFragment.onActivityResult(requestCode, resultCode, data);
    }

}
