package com.pubnub.di;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by clickapps on 2/11/17.
 */

public interface IPubnubProperty<T> {

    T channels(@NonNull String... channels);

    T channels(@NonNull List<String> channels);

    void build();
}
