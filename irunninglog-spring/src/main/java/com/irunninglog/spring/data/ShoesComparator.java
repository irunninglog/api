package com.irunninglog.spring.data;

import com.irunninglog.api.data.IShoe;

import java.util.Comparator;

final class ShoesComparator implements Comparator<IShoe> {

    @Override
    public int compare(IShoe o1, IShoe o2) {
        if ((o1.getStartDate() == null || o1.getStartDate().isEmpty())
                && (o2.getStartDate() == null || o2.getStartDate().isEmpty())) {
            return o1.getName().compareTo(o2.getName());
        } else if ((o1.getStartDate() == null || o1.getStartDate().isEmpty())
                && (o2.getStartDate() != null && !o2.getStartDate().isEmpty())) {
            return 1;
        } else if ((o1.getStartDate() != null && !o1.getStartDate().isEmpty())
                && (o2.getStartDate() == null || o2.getStartDate().isEmpty())) {
            return -1;
        } else {
            return o2.getStartDate().compareTo(o1.getStartDate());
        }
    }

}
