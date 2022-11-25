package com.negd.umangwebview.data;


import com.negd.umangwebview.data.local.pref.IPreferencesHelper;
import com.negd.umangwebview.data.local.pref.ISecuredPreferencesHelper;
import com.negd.umangwebview.data.remote.ApiHelper;

public interface DataManager extends ApiHelper, IPreferencesHelper, ISecuredPreferencesHelper {


}
