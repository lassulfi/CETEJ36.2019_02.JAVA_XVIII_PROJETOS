package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.R;

public class AlertDialogUtils {
    
    public static void showConfirmActionDialog(Context context, String message, 
                                               DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirm);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, listener);
        builder.setNegativeButton(R.string.no, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
