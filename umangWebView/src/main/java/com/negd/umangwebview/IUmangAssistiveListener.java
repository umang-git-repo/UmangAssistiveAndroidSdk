package com.negd.umangwebview;

import android.content.Context;

/**
 * An interface to listen SDK actions
 */
public interface IUmangAssistiveListener {
    /**
     * method to receive callback on click action of Header Back icon or Custom Header view or parametrized view
     */
    void onHeaderClickAction(Context context);

    /**
     * method to receive callback on click action of Footer Header view or parametrized view
     */
    void onFooterClickAction(Context context);

    /**
     * method to receive callback when SDK fails to initialize
     */
    void onSdkInitializationError(String error);
}
