package com.negd.umangwebview.callbacks;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.webkit.JavascriptInterface;

import com.negd.umangwebview.ui.UmangWebActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ContactInterface {

    private String TAG = "ContactInterface";
    private UmangWebActivity mAct;
    private List<Contactitem> contactsList;


    private class Contactitem{
        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactNum() {
            return contactNum;
        }

        public void setContactNum(String contactNum) {
            this.contactNum = contactNum;
        }

        private String contactName;
        private String contactNum;
    }

    public ContactInterface(UmangWebActivity act) {
        this.mAct = act;
    }

    @JavascriptInterface
    public void getPhoneContacts(String success,String failue){

        JSONArray contactArr = new JSONArray();

        Cursor phones = mAct.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext())
        {
            @SuppressLint("Range") String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            try {
                JSONObject contactObj = new JSONObject();
                contactObj.put("name", name);
                contactObj.put("phoneNum", phoneNumber);
                contactArr.put(contactObj);
            }
            catch (Exception e){
                mAct.sendContacts("F",contactArr.toString(),success,failue);
            }

        }
        phones.close();
        mAct.sendContacts("S",contactArr.toString(),success,failue);
    }
}
