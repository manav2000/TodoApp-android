package Components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView date;

    public DatePickerFragment(TextView date) {
        this.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.DatePickerTheme, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dd = (day >= 10) ? String.valueOf(day) : "0"+String.valueOf(day);
        String mm = (month >= 10) ? String.valueOf(month) : "0"+String.valueOf(month);
        String yy = (year >= 10) ? String.valueOf(year) : "0"+String.valueOf(year);
        date.setText(dd + "/" + mm + "/" + yy);
    }
}

