package art.com.photogallery.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import art.com.photogallery.Params.Params;
import art.com.photogallery.fragments.GalleryFragment;
import art.com.photogallery.fragments.SettingsFragment;

public class GalleryPagerAdapter extends FragmentPagerAdapter {
    private Map<Integer, String> fragmentTags;
    private FragmentManager fragmentManager;

    public GalleryPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.fragmentTags = new HashMap<>();
    }

    @Override
    public int getCount() {
        return Params.FRAGMENTS_AMOUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Params.GALLERY_FRAGMENT:
                return GalleryFragment.newInstance();
            case Params.SETTINGS_FRAGMENT:
                return SettingsFragment.newInstance();
            default:
                return GalleryFragment.newInstance();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            fragmentTags.put(position, fragment.getTag());
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = fragmentTags.get(position);
        if (tag != null) {
            fragment = fragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }

}
