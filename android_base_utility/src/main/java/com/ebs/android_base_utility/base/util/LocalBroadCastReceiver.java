package com.ebs.android_base_utility.base.util;

import android.content.Context;

/**
 * Created by barbaros.vasile on 1/12/2017.
 */

public interface LocalBroadCastReceiver {
    void Receive(Context context, String action, String sender, String json);
}
