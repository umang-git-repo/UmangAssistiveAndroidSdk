package com.negd.umangwebview;

/**
 * An interface to listen SDK actions
 */
public interface IUmangAssistiveListener {
    /**
     * method to receive callback on click action of Header Back icon or Custom Header view or parametrized view
     */
    void onHeaderClickAction();

    /**
     * method to receive callback on click action of Footer Header view or parametrized view
     */
    void onFooterClickAction();
}
