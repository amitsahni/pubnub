package com.pubnubutil.di;

import com.pubnubutil.HistoryBuilder;
import com.pubnubutil.PublishBuilder;
import com.pubnubutil.SubScribeBuilder;

import java.util.List;

/**
 * Created by clickapps on 2/11/17.
 */

public interface IPubnubProperty {

    List<String> getScribeList();

    SubScribeBuilder subScribe();

    SubScribeBuilder unSubScribe();

    SubScribeBuilder unSubScribeAll();

    PublishBuilder publish();

    HistoryBuilder history();

    void enableGCM();

    void disableGCM();
}
