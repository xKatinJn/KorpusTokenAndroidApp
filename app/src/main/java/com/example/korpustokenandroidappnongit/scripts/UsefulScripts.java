package com.example.korpustokenandroidappnongit.scripts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.korpustokenandroidappnongit.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsefulScripts {
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    public static void MakeToastError(Context context, String message, String color, Boolean length) {
        Toast toast;
        if (length) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }else{
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        TextView text = toast.getView().findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor(color));
        toast.show();
    }

    public static void MakeToastInternetError(Context context) {
        Toast toast = Toast.makeText(context, R.string.internet_error, Toast.LENGTH_SHORT);
        TextView text = toast.getView().findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FF0000"));
        toast.show();
    }

    public static void SetEditTextsDrawable(List<EditText> views, int drawable) {
        for (EditText view : views){
            view.setBackgroundResource(drawable);
        }
    }

    public static void SetEditTextsHint(List<EditText> views, int hint) {
        for (EditText view : views){
            view.setHint(hint);
        }
    }

    public static boolean RegistrationEmptyFieldValidation(EditText field) {
        if (field.getText().toString().equals("")){
            field.setBackgroundResource(R.drawable.rounded_edittext_error);
            return false;
        }
        field.setBackgroundResource(R.drawable.rounded_edittext);
        return true;
    }

    public static boolean EmptyFieldValidation(List<EditText> fields) {
        boolean flag = true;

        for (EditText field : fields){
            if (field.getText().toString().equals("")){
                field.setBackgroundResource(R.drawable.rounded_edittext_error);
                flag = false;
            }else{
                field.setBackgroundResource(R.drawable.rounded_edittext);
            }
        }
        return flag;
    }

    public static boolean RegistrationEmailValidation(EditText email) {
        if (!RegistrationEmptyFieldValidation(email)){
            return false;
        }
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email.getText().toString());
        if (!m.matches()){
            email.setBackgroundResource(R.drawable.rounded_edittext_error);
            email.getText().clear();
            email.setHint(R.string.email_format_error);
            return false;
        }
        return true;
    }

    public static boolean RegistrationPasswordValidation(EditText password, EditText password_repeat) {
        //IS EMPTY
        if (!EmptyFieldValidation(Arrays.asList(password, password_repeat))){
            password.setHint(R.string.password);
            password_repeat.setHint(R.string.password_repeat);
            return false;
        //OK
        }else if (password.getText().toString().equals(password_repeat.getText().toString())){
            UsefulScripts.SetEditTextsDrawable(Arrays.asList(password, password_repeat), R.drawable.rounded_edittext);
            password.setHint(R.string.password);
            password_repeat.setHint(R.string.password_repeat);
            return true;
        //NOT EQUAL
        }else{
            password.getText().clear();
            password_repeat.getText().clear();
            UsefulScripts.SetEditTextsDrawable(Arrays.asList(password, password_repeat), R.drawable.rounded_edittext_error);
            UsefulScripts.SetEditTextsHint(Arrays.asList(password, password_repeat), R.string.password_error);
            return false;
        }
    }

    public static boolean RegistrationValidation(List<EditText> fields, EditText email, EditText password, EditText password_repeat) {
        boolean flag = true;
        if (!RegistrationPasswordValidation(password, password_repeat)){
            flag = false;
        }
        if (!RegistrationEmailValidation(email)){
            flag = false;
        }
        if (!EmptyFieldValidation(fields)){
            flag = false;
        }
        return flag;
    }

    public static int ConvertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static void ReloadActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(activity.getIntent());
        activity.overridePendingTransition(0, 0);
    }
}
