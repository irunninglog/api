package com.irunninglog.workout;

import com.irunninglog.Progress;

@SuppressWarnings("unused")
public final class WorkoutsSummary {

    /*
    {"workouts":[{"id":819275,"title":"I ran for #distance","date":"01-27-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819274,"title":"I ran for #distance","date":"01-26-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819273,"title":"I ran for #distance","date":"01-25-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819272,"title":"I ran for #distance","date":"01-24-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819271,"title":"I ran for #distance","date":"01-23-2017","distance":"2 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819269,"title":"I ran #distance in #duration (#pace pace)","date":"01-22-2017","distance":"6 mi","duration":"48:37","pace":"08:06","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819270,"title":"I ran #distance in #duration (#pace pace)","date":"01-21-2017","distance":"5 mi","duration":"41:14","pace":"08:14","privacy":"Private","route":{"id":24,"name":"NYC West Side","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819268,"title":"I ran for #distance","date":"01-20-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819267,"title":"I ran for #distance","date":"01-19-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819266,"title":"I ran for #distance","date":"01-18-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819265,"title":"I ran for #distance","date":"01-17-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819264,"title":"I ran #distance in #duration (#pace pace)","date":"01-16-2017","distance":"5 mi","duration":"42:12","pace":"08:26","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":12,"name":"Recovery","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819263,"title":"I ran #distance in #duration (#pace pace)","date":"01-15-2017","distance":"7 mi","duration":"57:06","pace":"08:09","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819262,"title":"I ran #distance in #duration (#pace pace)","date":"01-14-2017","distance":"7 mi","duration":"56:30","pace":"08:04","privacy":"Private","route":{"id":24,"name":"NYC West Side","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819261,"title":"I ran for #distance","date":"01-13-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819260,"title":"I ran for #distance","date":"01-12-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819259,"title":"I ran for #distance","date":"01-11-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819258,"title":"I ran for #distance","date":"01-10-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819257,"title":"I ran for #distance","date":"01-09-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819256,"title":"I ran for #distance","date":"01-08-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":12,"name":"Recovery","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819254,"title":"I ran #distance in #duration (#pace pace)","date":"01-07-2017","distance":"6.21 mi","duration":"50:50","pace":"08:11","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":11,"name":"Race","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819255,"title":"I ran for #distance","date":"01-07-2017","distance":"3.79 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":12,"name":"Recovery","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819253,"title":"I ran for #distance","date":"01-06-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819252,"title":"I ran for #distance","date":"01-05-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819251,"title":"I ran for #distance","date":"01-04-2017","distance":"4 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819250,"title":"I ran for #distance","date":"01-03-2017","distance":"3 mi","duration":"--","pace":"--","privacy":"Private","route":{"id":22,"name":"GS Treadmill","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":9,"name":"Nu","description":"Mizuno Wave Inspire 11 (Pink)"}},{"id":819249,"title":"I ran #distance in #duration (#pace pace)","date":"01-02-2017","distance":"7 mi","duration":"56:50","pace":"08:07","privacy":"Private","route":{"id":16,"name":"Central Park","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}},{"id":819248,"title":"I ran #distance in #duration (#pace pace)","date":"01-01-2017","distance":"4.2 mi","duration":"34:08","pace":"08:07","privacy":"Private","route":{"id":24,"name":"NYC West Side","description":null},"run":{"id":10,"name":"General Aerobic","description":null},"shoe":{"id":10,"name":"Xi","description":"Mizuno Wave Inspire 11 (Green)"}}],"key":"01-01-2017","summary":{"title":"January 2017","count":28,"mileage":"119.2 mi","progress":"Good","percentage":95},"previous":{"title":"December 2016","date":"12-01-2016"},"next":null}
     */

    private String title;
    private int count;
    private Progress progress;
    private String mileage;
    private int percentage;

    public String getTitle() {
        return title;
    }

    public WorkoutsSummary setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCount() {
        return count;
    }

    public WorkoutsSummary setCount(int count) {
        this.count = count;
        return this;
    }

    public Progress getProgress() {
        return progress;
    }

    public WorkoutsSummary setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public WorkoutsSummary setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public int getPercentage() {
        return percentage;
    }

    public WorkoutsSummary setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

}
