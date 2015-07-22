package com.cablevision.cambaceomovil;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cablevision.cambaceomovil.R;

public class Swipe_Section_Fragments extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe__section__fragment);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe__section_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        String [] images;
        String [] information;


        public SectionsPagerAdapter(FragmentManager fm,Context context) {
            super(fm);
            Resources resources = context.getResources();
            images = resources.getStringArray(R.array.imagenes);
            information = resources.getStringArray(R.array.informacion);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.ImageKey,getImageId(position));
            bundle.putString(PlaceholderFragment.TxtKey,information[position]);

            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            placeholderFragment.setArguments(bundle);

            return placeholderFragment;
        }


        private int getImageId(int i){
            int id=0;
            switch (i){
                case 0:
                    id=R.drawable.permanencia;
                    break;
                case 1:
                    id=R.drawable.promociones;
                    break;
                case 2:
                    id=R.drawable.paquete;
                    break;
                case 3:
                    id=R.drawable.individuales;
                    break;
                case 4:
                    id=R.drawable.competencia;
                    break;
            }
            return id;
        }


        @Override
        public int getCount() {

            return images.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ImageKey = "image";
        private static final String TxtKey ="info";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
      /*  public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        } */

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_swipe__section__fragments, container, false);

            Bundle bundle = getArguments();
            if (bundle != null){
                int imageKey = bundle.getInt(ImageKey);
                String txtKey = bundle.getString(TxtKey);

                displayVales(rootView,imageKey,txtKey);

            }


            return rootView;
        }

        private void displayVales(View rootView, int imageKey, String txtKey) {
            ImageView imageView = (ImageView)rootView.findViewById(R.id.fragment_imageView);
            imageView.setImageResource(imageKey);

            TextView textView = (TextView)rootView.findViewById(R.id.fragment_txt);
            textView.setText(txtKey);
        }
    }

}
