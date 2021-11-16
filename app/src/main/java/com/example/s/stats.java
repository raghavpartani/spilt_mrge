package com.example.s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.math.Stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class stats extends AppCompatActivity {

    TextView tv;
    Spinner yearspinner;
    String msgData = "";
    static String amts = "";
    String avail = "";
    static String amtsfordisplay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        tv = findViewById(R.id.textView);

        yearspinner = findViewById(R.id.year);

        setspinner();

        //   tv.setText();
        //    TextV
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 1);
//        } else {
//            String SelectedDate[] = month.getSelectedItem().toString().split(" ", 2);
//            msgData = getAllSms(this, SelectedDate[0], SelectedDate[1]);
//        }
    }

    private void setspinner() {
        ArrayList<String> yeararraylist = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        yeararraylist.add(year + "");
        yeararraylist.add(year - 1 + "");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yeararraylist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearspinner.setAdapter(arrayAdapter);

        yearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ContextCompat.checkSelfPermission(stats.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(stats.this, new String[]{Manifest.permission.READ_SMS}, 1);
                } else {
                    msgData = getAllSms(stats.this, yearspinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public String getAllSms(Context context, String year) {
        double jantotal = 0.0;
        double febtotal = 0.0;
        double martotal = 0.0;
        double aprtotal = 0.0;
        double maytotal = 0.0;
        double juntotal = 0.0;
        double jultotal = 0.0;
        double augtotal = 0.0;
        double septotal = 0.0;
        double octtotal = 0.0;
        double novtotal = 0.0;
        double dectotal = 0.0;

        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        String s = "";
        double totaldeb = 0;
        Pattern regEx = Pattern.compile("(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Aa][Cc][Cc][Tt].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)");


        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    String bodylowercase = body.toLowerCase();
                    Date dateFormat = new Date(Long.valueOf(smsDate));
                    long millisecond = Long.parseLong(smsDate);

                    String dateString = DateFormat.format("dd/MMM/yyyy", new Date(millisecond)).toString();
                    String type;

                    String splitedate[] = dateString.split("/");
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";

                            if (number.length() == 10 || number.length() == 13) {
                                s = s + "NULL message";
                            } else {
                                Matcher m = regEx.matcher(bodylowercase);
                                String amts = "";

                                if (m.find()) {
                                    if (splitedate[2].trim().equals(year)) {
                                        if (bodylowercase.contains("debited") || bodylowercase.contains("paid") || bodylowercase.contains("spent")) {
                                            amts = getamts(bodylowercase, "debited");
                                            if (amts.length() > 0) {
                                                if (amts.charAt(0) == '.') {
                                                    String am[] = amts.split(".", 2);
                                                    amts = am[1];
                                                }

                                                if (splitedate[1].trim().equalsIgnoreCase("jan")) {
                                                    jantotal = jantotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("feb")) {
                                                    febtotal = febtotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("mar")) {
                                                    martotal = martotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("apr")) {
                                                    aprtotal = aprtotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("may")) {
                                                    maytotal = maytotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("jun")) {
                                                    juntotal = juntotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("jul")) {
                                                    jultotal = jultotal + Double.parseDouble(amts);
                                                } else if (splitedate[1].trim().equalsIgnoreCase("aug")) {
                                                    augtotal = augtotal + Double.parseDouble(amts);

                                                } else if (splitedate[1].trim().equalsIgnoreCase("sep")) {
                                                    septotal = septotal + Double.parseDouble(amts);

                                                } else if (splitedate[1].trim().equalsIgnoreCase("oct")) {
                                                    octtotal = octtotal + Double.parseDouble(amts);

                                                } else if (splitedate[1].trim().equalsIgnoreCase("nov")) {
                                                    novtotal = novtotal + Double.parseDouble(amts);

                                                } else if (splitedate[1].trim().equalsIgnoreCase("dec")) {
                                                    dectotal = dectotal + Double.parseDouble(amts);

                                                }
                                                //    totaldeb = totaldeb + Double.parseDouble(amts);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
//                        case Telephony.Sms.MESSAGE_TYPE_SENT:
//                            type = "sent";
//                            break;
//                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
//                            type = "outbox";
//                            break;
                        default:
                            break;
                    }
                    c.moveToNext();

                }
                tv.setText(jantotal + "\n" + febtotal + "\n" + martotal + "\n" + aprtotal + "\n" + maytotal + "\n" + juntotal + "\n" + jultotal + "\n" + augtotal + "\n" + septotal + "\n" + octtotal + "\n" + novtotal + "\n" + dectotal);

            }
            c.close();

        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return year;
    }

    private String getamts(String bodylowercase, String creordeb) {
        String amt[] = {};
        amts="";
        amtsfordisplay = "";
        if (bodylowercase.contains(" inr")) {
            amt = bodylowercase.split("inr", 2);
        } else if (bodylowercase.contains("rs")) {
            amt = bodylowercase.split("rs", 2);
        }
        for (int i = 0; i < amt[1].length(); i++) {
            if (amt[1].charAt(i) >= 48 && amt[1].charAt(i) <= 57 || amt[1].charAt(i) == ' ' || amt[1].charAt(i) == ',' || amt[1].charAt(i) == '.') {
                if (amt[1].charAt(i) == ',') {
                    amtsfordisplay = amtsfordisplay + amt[1].charAt(i);
                } else {
                    amts = amts + amt[1].charAt(i);
                    amtsfordisplay = amtsfordisplay + amt[1].charAt(i);
                }
            } else {
                break;
            }
        }
        //amts = "\n" + creordeb + " amount" + amts;
        return amts;
    }
}