package com.scopus.ifsp.projetofinalteste.activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.model.Alarme;
import com.scopus.ifsp.projetofinalteste.model.AlarmeDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Remedio;
import com.scopus.ifsp.projetofinalteste.model.RemedioDao;
import com.scopus.ifsp.projetofinalteste.service.AlarmReceiver;
import com.scopus.ifsp.projetofinalteste.service.AlarmScheduler;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

public class AddReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    String TAG = "AddReminderActivity.class";
    public String nomeRemedioReminder;
    private TextView mTitleText, mDateInicioText, mDateFinalText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private CheckBox cbDataFinal;
    private FloatingActionButton mFAB1, mFAB2;
    private Calendar mCalendar;
    private int mYear_Initial, mMonth_Initial, mHour, mMinute, mDay_initial;
    private int mYear_Final, mMonth_Final, mDay_Final;
    private long mRepeatTime;
    private Switch mRepeatSwitch;
    private String mTime, mDate_Initial;
    private String mDate_Final;
    private String mRepeat;
    private String mRepeatType;
    private String mActive;
    private String mRepeatNo;
    private int mAtiva_UltimaData;
    TextView LastDateTitule;
    CheckBox btUltimaData;
    ImageView imgUltimaData;

    int idRemedio = 0;
    Alarme alarmeEditado = null;
    int idAlarme = 0;

    private boolean mVehicleHasChanged = false;

    private PendingIntent pendingIntent;

    //Alarm Request Code
    private int ALARM_REQUEST_CODE = 10000;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        final Intent intent = getIntent();
        // Initialize Views
        mTitleText = findViewById(R.id.alarmNomeRemedio);
        mDateInicioText = findViewById(R.id.setInicio_date);
        mDateFinalText = findViewById(R.id.setFinal_date);
        mTimeText = findViewById(R.id.set_time);
        mRepeatText = findViewById(R.id.set_repeat);
        mRepeatNoText = findViewById(R.id.set_repeat_no);
        mRepeatTypeText = findViewById(R.id.set_repeat_type);
        mRepeatSwitch = findViewById(R.id.repeat_switch);
        mFAB1 = findViewById(R.id.starred1);
        mFAB2 = findViewById(R.id.starred2);
        btUltimaData = findViewById(R.id.radioButton);
        imgUltimaData = findViewById(R.id.dateFinal_icon);
        LastDateTitule = findViewById(R.id.dateFinal_text);

        if (intent.hasExtra("remedioAlarme")) {
            Remedio remedio = (Remedio) intent.getSerializableExtra("remedioAlarme");

            idRemedio = (int) (long)remedio.getId();
            nomeRemedioReminder = remedio.getNome_remedio();

            mCalendar = Calendar.getInstance();
            mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
            mMinute = mCalendar.get(Calendar.MINUTE);
            mYear_Initial = mCalendar.get(Calendar.YEAR);
            mMonth_Initial = mCalendar.get(Calendar.MONTH) + 1;
            mDay_initial = mCalendar.get(Calendar.DATE);

            mDate_Initial = verifica_data(mDay_initial) + "/" + verifica_mes(mMonth_Initial) + "/" + mYear_Initial;
            mDate_Final = verifica_data(mDay_initial) + "/" + verifica_mes(mMonth_Initial) + "/" + mYear_Initial;
            mTime = verifica_Hora(mHour) + ":" + verifica_Minuto(mMinute);


            mDateInicioText.setText(mDate_Initial);
            btUltimaData.setChecked(false);
            mDateFinalText.setText(mDate_Final);
            mTimeText.setText(mTime);

            mRepeat = "true";
            mRepeatType = "Hora";
            mActive = "true";

            mRepeatNo = Integer.toString(1);
            mRepeatNoText.setText(mRepeatNo);
            mRepeatTypeText.setText(mRepeatType);
            mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));

        } else if (intent.hasExtra("alarme")) {

            mCalendar = Calendar.getInstance();
            alarmeEditado = (Alarme) intent.getSerializableExtra("alarme");

            idAlarme = (int) (long)alarmeEditado.getId();
            mAtiva_UltimaData = alarmeEditado.getAtivar_ultima_data();


            if (mAtiva_UltimaData == 1) {
                btUltimaData.setChecked(true);
            }
            mRepeat = alarmeEditado.getRepeat();
            mRepeatType = alarmeEditado.getRepeat_type();
            mRepeatNo = alarmeEditado.getRepeat_no();
            mActive = alarmeEditado.getActive();

            idRemedio = (int) (long)alarmeEditado.getRemedio().getId();
            nomeRemedioReminder = alarmeEditado.getRemedio().getNome_remedio();
            mTitleText.setText(alarmeEditado.getTitulo());
            mDateInicioText.setText(alarmeEditado.getData());
            mDateFinalText.setText(alarmeEditado.getUltima_data());
            mTimeText.setText(alarmeEditado.getTime());

            if (mRepeat.equals("true")){
                mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));
            }else {
                mRepeatText.setText(R.string.Switch_textOff);

            }
            mRepeatNoText.setText(alarmeEditado.getRepeat_no());
            mRepeatTypeText.setText(alarmeEditado.getRepeat_type());
            mActive = alarmeEditado.getActive();

            Log.i(TAG, "onCreate: " + alarmeEditado.getTime());

            String[] tempo = alarmeEditado.getTime().split(":");
            String[] data = alarmeEditado.getData().split("/");

            mTime = alarmeEditado.getTime();
            mDate_Initial = alarmeEditado.getData();

            mHour = Integer.parseInt(tempo[1]);
            mMinute = Integer.parseInt(tempo[0]);
            mYear_Initial = Integer.parseInt(data[2]);
            mMonth_Initial = Integer.parseInt(data[1]);
            mDay_initial = Integer.parseInt(data[0]);

        } else {
            Toast.makeText(this, "Falhou", Toast.LENGTH_LONG).show();
        }

        verificaUltimaDataAtivado();
        mTitleText.setText(nomeRemedioReminder);

        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.discard_reminder);

        final Intent intent = getIntent();

        if (intent.hasExtra("alarme")) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }

        return true;
    }


    // On clicking Time picker
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    // On clicking Date picker
    public void setLastDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "DatepickerLastDate");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;

        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        switch (view.getTag()) {
            case "Datepickerdialog":

                mDay_initial = dayOfMonth;
                mMonth_Initial = monthOfYear;
                mYear_Initial = year;
                mDate_Initial = verifica_data(dayOfMonth) + "/" + verifica_mes(monthOfYear) + "/" + year;
                mDateInicioText.setText(mDate_Initial);

                break;
            case "DatepickerLastDate":

                mDay_Final = dayOfMonth;
                mMonth_Final = monthOfYear;
                mYear_Final = year;
                mDate_Final = verifica_data(dayOfMonth) + "/" + verifica_mes(monthOfYear) + "/" + year;
                mDateFinalText.setText(mDate_Final);
            break;

            default:
                Toast falhou = Toast.makeText(this, "Falhou", Toast.LENGTH_SHORT);
                falhou.show();
                break;
        }

    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));
        } else {
            mRepeat = "false";
            mRepeatText.setText(R.string.Switch_textOff);
        }
    }

    // On clicking repeat type button
    public void selectRepeatType(View v) {
        final String[] items = new String[5];

        items[0] = "Minuto";
        items[1] = "Hora";
        items[2] = "Dia";
        items[3] = "Semana";
        items[4] = "Mês";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecionar Tipo");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking repeat interval button
    public void setRepeatNo(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Intervalo");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));
                        } else {
                            mRepeatNo = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText(String.format("A cada %s %s (s)", mRepeatNo, mRepeatType));
                        }
                    }
                });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    public String verifica_Hora(int mHora) {
        if (mHora < 10) {
            return "0" + mHora;
        } else {
            return Integer.toString(mHora);
        }
    }


    public String verifica_Minuto(int mMinute) {
        if (mMinute < 10) {
            return "0" + mMinute;
        } else {
            return Integer.toString(mMinute);
        }
    }

    public String verifica_data(int mDate) {
        if (mDate < 10) {
            return "0" + Integer.toString(mDate);
        } else {
            return Integer.toString(mDate);
        }
    }

    public String verifica_mes(int mMes) {
        if (mMes < 10) {
            return "0" + mMes;
        } else {
            return Integer.toString(mMes);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.save_reminder:
                saveReminder();
                finish();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.discard_reminder:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the reminder.
                deleteReminder();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteReminder() {
        Intent alarmIntent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
        // Show a toast message depending on whether or not the insertion was successful.

        //Componentes BD
        DaoSession daoSession = ((App) this.getApplicationContext()).getDaoSession();
        AlarmeDao alarmeDao = daoSession.getAlarmeDao();
        alarmeDao.delete(alarmeEditado);

        ALARM_REQUEST_CODE = ALARM_REQUEST_CODE + idAlarme;

        pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, ALARM_REQUEST_CODE , alarmIntent, 0);

       new AlarmScheduler().cancelAlarm(getApplicationContext(), pendingIntent, idAlarme);
            Toast.makeText(this, getString(R.string.editor_delete_reminder_successful),
                    Toast.LENGTH_SHORT).show();
        finish();
    }

    // On clicking the save button
    public void saveReminder() {

        String titulo = mTitleText.getText().toString();
        String data = mDateInicioText.getText().toString();
        String ultima_data = mDateFinalText.getText().toString();
        int ativa_ultima_data;
        String time = mTimeText.getText().toString();
        String repeat = mRepeat;
        String repeatNo = mRepeatNoText.getText().toString();
        String repeatType = mRepeatTypeText.getText().toString();
        String active = mActive;

        if(btUltimaData.isChecked()){
            ativa_ultima_data = 1;
        }else {
            ativa_ultima_data = 0;
        }
        //Componentes BD
        DaoSession daoSession = ((App) this.getApplicationContext()).getDaoSession();
        RemedioDao remedioDao = daoSession.getRemedioDao();
        AlarmeDao alarmeDao = daoSession.getAlarmeDao();

        Remedio remedio = remedioDao.load((long)idRemedio);

        boolean sucesso = false;
        boolean novo = false;

        //Se for um Alarme existente, atualizar dados remédio
        if (alarmeEditado != null) {
            Alarme alarme = new Alarme();
            alarme.setId(alarmeEditado.getId());
            alarme.setTitulo(titulo);
            alarme.setData(data);
            alarme.setUltima_data(ultima_data);
            alarme.setTime(time);
            alarme.setRepeat(repeat);
            alarme.setRepeat_no(repeatNo);
            alarme.setRepeat_type(repeatType);
            alarme.setActive(active);
            alarme.setAtivar_ultima_data(ativa_ultima_data);
            alarme.setRemedio(remedio);

            alarmeDao.insertOrReplace(alarme);
            sucesso = true;
        } else {
            try{
                //Se não, salvar novo Alarme

                Alarme alarme = new Alarme();
                alarme.setId(alarmeEditado.getId());
                alarme.setTitulo(titulo);
                alarme.setData(data);
                alarme.setUltima_data(ultima_data);
                alarme.setTime(time);
                alarme.setRepeat(repeat);
                alarme.setRepeat_no(repeatNo);
                alarme.setRepeat_type(repeatType);
                alarme.setActive(active);
                alarme.setAtivar_ultima_data(ativa_ultima_data);
                alarme.setRemedio(remedio);

                alarmeDao.insert(alarme);
                sucesso = true;
                if (sucesso) {
                    novo = true;}

            }catch (Exception e){
                Log.i(TAG, "saveReminder: " + e);
            }
        }

        if (sucesso) {


            // Set up calender for creating the notification
            mCalendar.set(Calendar.MONTH, --mMonth_Initial);
            mCalendar.set(Calendar.YEAR, mYear_Initial);
            mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
            mCalendar.set(Calendar.MINUTE, mMinute);
            mCalendar.set(Calendar.SECOND, 0);

            long selectedTimestamp = mCalendar.getTimeInMillis();

            // Check repeat type
            switch (mRepeatType) {
                case "Minuto":
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                    break;
                case "Hora":
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                    break;
                case "Dia":
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                    break;
                case "Semana":
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                    break;
                case "Mês":
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                    break;
            }

            // Show a toast message depending on whether or not the insertion was successful.
            if (novo) {

                List<Alarme> alarmes = alarmeDao.loadAll();
                Alarme novoAlarme = alarmes.get(alarmes.size() - 1);

                idAlarme = (int) (long)novoAlarme.getId();

                Toast.makeText(this, getString(R.string.editor_insert_reminder_successful),
                        Toast.LENGTH_SHORT).show();

            } else {

                idAlarme = (int) (long)alarmeEditado.getId();

                Toast.makeText(this, getString(R.string.editor_update_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }

            Intent alarmIntent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
            Bundle bundle = new Bundle();

            bundle.putInt("idAlarme", idAlarme);

            alarmIntent.putExtras(bundle);

            ALARM_REQUEST_CODE = ALARM_REQUEST_CODE + idAlarme;

            pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, ALARM_REQUEST_CODE , alarmIntent, 0);

            // Create a new notification
            if (mActive.equals("true")) {
                if (mRepeat.equals("true")) {

                        new AlarmScheduler().setRepeatAlarm(getApplicationContext(), selectedTimestamp, mRepeatTime, pendingIntent);

                } else if (mRepeat.equals("false")) {

                    new AlarmScheduler().setAlarm(getApplicationContext(), selectedTimestamp,pendingIntent);

                }

                Toast.makeText(this, "Tempo de alarme é " + selectedTimestamp,
                        Toast.LENGTH_LONG).show();
            } else if (mActive.equals("false")) {

                    new AlarmScheduler().cancelAlarm(getApplicationContext(), pendingIntent,idAlarme);

            }

            Intent intentAlarme = new Intent(this, AlarmActivity.class);
            Remedio retornaRemedio;
            retornaRemedio = remedioDao.load((long)idRemedio);
            intentAlarme.putExtra("remedio", retornaRemedio);
            startActivity(intentAlarme);
            // Create toast to confirm new reminder
            Toast.makeText(getApplicationContext(), "Salvo",
                    Toast.LENGTH_SHORT).show();


        } else {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_reminder_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void verificaUltimaDataAtivado(){
        if (btUltimaData.isChecked()) {
            imgUltimaData.setImageResource(R.drawable.ic_view_day_black);
            LastDateTitule.setTextColor(getColor(R.color.grey31));
            mDateFinalText.setTextColor(getColor(R.color.grey31));
        } else {
            imgUltimaData.setImageResource(R.drawable.ic_view_day_off);
            LastDateTitule.setTextColor(getColor(R.color.cinza));
            mDateFinalText.setTextColor(getColor(R.color.cinza));
        }
    }

    public void btRadioLD(View view) {
        if (btUltimaData.isChecked()) {
            imgUltimaData.setImageResource(R.drawable.ic_view_day_black);
            LastDateTitule.setTextColor(getColor(R.color.grey31));
            mDateFinalText.setTextColor(getColor(R.color.grey31));
        } else {
            imgUltimaData.setImageResource(R.drawable.ic_view_day_off);
            LastDateTitule.setTextColor(getColor(R.color.cinza));
            mDateFinalText.setTextColor(getColor(R.color.cinza));
        }
    }
}
