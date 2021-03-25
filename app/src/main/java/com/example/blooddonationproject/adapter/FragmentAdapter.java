package com.example.blooddonationproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.blooddonationproject.fragments.Fragment1;
import com.example.blooddonationproject.fragments.Fragment2;
import com.example.blooddonationproject.fragments.Fragment3;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int count=0;

    public FragmentAdapter(@NonNull FragmentManager fm, int count) {
        super(fm,count);
        this.count=count;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: Fragment1 one=new Fragment1();
                return one;

            case 1: Fragment2 two=new Fragment2();
                return two;
            case 2: Fragment3 three=new Fragment3();
                return three;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return count;
    }
}
