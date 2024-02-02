package com.example.simple_calculator3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
//private static final String CHANNEL_id = "hey guys";
//private static final int REQ_CODE = 1;

    //private static final int NOTIFICATION_id = 10;
    TextView inputText, outputText;
    private MenuItem History;


    private String input, output, newOutput;

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonAdd, buttonMultiply, buttonSubs, buttonDivision, buttonPoint, buttonPercent,
            buttonBackspace, buttonEqual, buttonClear;

    //EditText editTextFileName, editTextData;
    Button equal_bt;


    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the permission has not been granted yet
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission hasn't been granted, so request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            // Permission is already granted
            // You can proceed with your file operations
        }


        // Check if the activity was launched from a notification
        Intent intent = getIntent();
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_MAIN)) {
            // Activity launched from the notification
            // Add your handling logic here
            // For example, bring the app to the foreground or display specific content
            // You can add your own logic here based on your app requirements
        } else {
            // Normal activity launch
            // Add your normal initialization logic here
        }



        //notification
       /*Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.img, null);

        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;

        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        Intent iNotify = new Intent(getApplicationContext(), MainActivity.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, REQ_CODE, iNotify, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.img)
                    .setContentText("Calculator running in background")
                    .setSubText("DONE with your CALCULATION?")
                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .setChannelId(CHANNEL_id)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_id, "New Channel", NotificationManager.IMPORTANCE_HIGH));
        }else{
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.img)
                    .setContentText("Calculator running in background")
                    .setSubText("DONE with your CALCULATION?")
                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .build();
        }
        nm.notify(NOTIFICATION_id, notification);*/


        //to remove the phone top tool where it has clock wifi etc..
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputText = findViewById(R.id.input_text);
        outputText = findViewById(R.id.output_text);

        button0 = findViewById(R.id.btn0);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button4 = findViewById(R.id.btn4);
        button5 = findViewById(R.id.btn5);
        button6 = findViewById(R.id.btn6);
        button7 = findViewById(R.id.btn7);
        button8 = findViewById(R.id.btn8);
        button9 = findViewById(R.id.btn9);
        buttonAdd = findViewById(R.id.addition_btn);
        buttonSubs = findViewById(R.id.substraction_btn);
        buttonMultiply = findViewById(R.id.multiply_btn);
        buttonDivision = findViewById(R.id.division_btn);
        buttonPercent = findViewById(R.id.percent_btn);
        buttonBackspace = findViewById(R.id.backspace_btn);
        buttonPoint = findViewById(R.id.btnpoint);
        buttonEqual = findViewById(R.id.equal_btn);
        buttonClear = findViewById(R.id.clear_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void onButtonClicked(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();

        if (input == null) {
            input = "";
        }



        if (data.equals("=")) {
            if (input.isEmpty()) {
                return;
            }
            solve();
        } else {
            switch (data) {
                case "AC":
                    input = null;
                    output = null;
                    newOutput = null;
                    outputText.setText("");
                    break;

                case "C":
                    if (!input.isEmpty()) {
                        input = input.substring(0, input.length() - 1);
                    }
                    break;

                case "*":
                    solve();
                    input += "*";
                    break;
                //case "=":
                //   solve();
                //   break;
                case "^":
                    input += "^";
                    double d = Double.parseDouble(inputText.getText().toString()) / 100;
                    outputText.setText(String.valueOf(d));
                    break;
                default:
                    if (input == null) {
                        input = "";
                    }
                    if (data.equals("+") || data.equals("/") || data.equals("-")) {
                        solve();
                    }
                    input += data;
            }

        }
        inputText.setText(input);


    }

    private void solve() {
        if (input.split("\\+").length == 2) {
            String numbers[] = input.split("\\+");
            try {
                double d = Double.parseDouble(numbers[0]) + Double.parseDouble(numbers[1]);
                output = Double.toString(d);
                newOutput = cutDecimal(output);
                outputText.setText(newOutput);
                input = d + "";
            } catch (Exception e) {
                outputText.setError(e.getMessage().toString());
            }
        }
        if (input.split("\\*").length == 2) {
            String numbers[] = input.split("\\*");
            try {
                double d = Double.parseDouble(numbers[0]) * Double.parseDouble(numbers[1]);
                output = Double.toString(d);
                newOutput = cutDecimal(output);
                outputText.setText(newOutput);
                input = d + "";
            } catch (Exception e) {
                outputText.setError(e.getMessage().toString());
            }
        }
        if (input.split("\\/").length == 2) {
            String numbers[] = input.split("\\/");
            try {
                double d = Double.parseDouble(numbers[0]) / Double.parseDouble(numbers[1]);
                output = Double.toString(d);
                newOutput = cutDecimal(output);
                outputText.setText(newOutput);
                input = d + "";
            } catch (Exception e) {
                outputText.setError(e.getMessage().toString());
            }
        }
        if (input.split("\\^").length == 2) {
            String numbers[] = input.split("\\^");
            try {
                double d = Math.pow(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                output = Double.toString(d);
                newOutput = cutDecimal(output);
                outputText.setText(newOutput);
                input = d + "";
            } catch (Exception e) {
                outputText.setError(e.getMessage().toString());
            }
        }

        if (input.split("\\-").length == 2) {
            String numbers[] = input.split("\\-");
            try {

                if (Double.parseDouble(numbers[0]) < Double.parseDouble(numbers[1])) {
                    double d = Double.parseDouble(numbers[1]) - Double.parseDouble(numbers[0]);
                    output = Double.toString(d);
                    newOutput = cutDecimal(output);
                    outputText.setText("-" + newOutput);
                    input = d + "";
                } else {
                    double d = Double.parseDouble(numbers[0]) - Double.parseDouble(numbers[1]);
                    output = Double.toString(d);
                    newOutput = cutDecimal(output);
                    outputText.setText(newOutput);
                    input = d + "";
                }

            } catch (Exception e) {
                outputText.setError(e.getMessage().toString());
            }
        }
        //view->tool windows->device file explorer->sdcard->Document
       /* if (newOutput != null) {
            try {
                // Create a FileWriter and BufferedWriter to write to the external storage
                //String timestamp = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss", Locale.getDefault()).format(new Date());
                File file = new File("/sdcard/Documents/RESULT", "calculator_result.txt");
                FileWriter writer = new FileWriter(file, true); // "true" for append mode
                BufferedWriter bufferWriter = new BufferedWriter(writer);

                // Append the result to the file
                bufferWriter.append(newOutput);
                bufferWriter.newLine();

                // Close the BufferedWriter
                bufferWriter.close();

                // Optionally, you can also show a Toast or log a message indicating the file has been saved
                // Toast.makeText(this, "Result saved to calculator_result.txt", Toast.LENGTH_SHORT).show();
              //  Log.d("Calculator", "Result saved to calculator_result.txt");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error message)
                // Toast.makeText(this, "Error saving result to file", Toast.LENGTH_SHORT).show();
            }

        }*/

        if (newOutput != null) {
            try {
                // Get current timestamp
                //String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                // Read existing data from the file, if any
                File directory = getPublicDocumentsStorageDir("RESULT");
                File file = getNextAvailableFile(directory, "calculator_result_",".txt");
                StringBuilder existingData = new StringBuilder();
                if (file.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        existingData.append(line).append("\n");
                    }
                    reader.close();
                }

                // Prepare the new result with timestamp
                String newData = timestamp + ": " + newOutput + "\n";

                // Combine the new result with existing data
                String combinedData = newData + existingData.toString();

                // Write the combined data back to the file
                FileWriter writer = new FileWriter(file);
                writer.write(newData);
                writer.flush();
                writer.close();

                Log.d("TAG", "Data written to file: " + newData);

                // Check if external storage is available for writing
                /*if (isExternalStorageWritable()) {`
                    // Get the directory for the user's public documents directory.
                    File directory = getPublicDocumentsStorageDir("RESULT");
                    if (directory != null) {
                        // Create file if it doesn't exist
                        File file = new File(directory, "calculator_result.txt");
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        // Create a FileWriter and BufferedWriter to write to the file
                        FileWriter writer = new FileWriter(file, true); // "true" for append mode
                        BufferedWriter bufferWriter = new BufferedWriter(writer);

                        // Append the result with timestamp to the file
                        bufferWriter.append(timestamp + ": " + newOutput);
                        bufferWriter.newLine();

                        // Close the BufferedWriter
                        bufferWriter.close();

                        // Optionally, you can also show a Toast or log a message indicating the file has been saved
                        // Toast.makeText(this, "Result saved to calculator_result.txt", Toast.LENGTH_SHORT).show();
                        // Log.d("Calculator", "Result saved to calculator_result.txt");
                    } else {
                        // Handle the case when the directory is null (e.g., storage not available)
                        // Toast.makeText(this, "External storage not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case when external storage is not writable
                    // Toast.makeText(this, "External storage not writable", Toast.LENGTH_SHORT).show();
                }*/
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error message)
                // Toast.makeText(this, "Error saving result to file", Toast.LENGTH_SHORT).show();
            }
        }
         showNotification();
    }

    // Method to get the next available file with an incremented name
    private File getNextAvailableFile(File directory, String baseName, String extension) {
        int counter = 1;
        File file;
        do {
            file = new File(directory, baseName + counter + extension);
            counter++;
        } while (file.exists());
        return file;
    }


// Method to check if external storage is available for writing

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Method to get the public documents directory
    public File getPublicDocumentsStorageDir(String dirName) {
        // Get the directory for the user's public documents directory.
        File file = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), dirName);
        }
        // Create the directory if it does not exist
        if (!file.mkdirs()) {
            Log.e("TAG", "Directory not created" + file.getAbsolutePath());
        }else{
            Log.d("TAG","Directory created:" + file.getAbsolutePath());
        }
        return file;
    }

   private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "calculator_channel";

    private void showNotification() {
        //check if the VIBRATE permission is granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
            != PackageManager.PERMISSION_GRANTED){
            //request the VIBRATE permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.VIBRATE}, 1);
        }else {
            // Create a notification channel (required for Android Oreo and above)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "Calculator Channel",
                        NotificationManager.IMPORTANCE_HIGH
                );

                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                channel.setDescription("Calculator is running in background");

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            //create an explicit intent for the Main Activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Calculator is running in the background")
                    .setContentText("Performing calculations")
                    .setSmallIcon(R.drawable.img)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);



            // Show the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, builder.build()); // Use a unique ID for each notification
        }
    }

    private String cutDecimal(String number){
        String n[] = number.split("\\.");
        if(n.length > 1){
            if(n[1].equals("0")){
                number = n[0];
            }
        }
        return number;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history:
                //Toast.makeText(this, "you clicked on history", Toast.LENGTH_SHORT).show();
                showHistory();
                break;
            case R.id.exit:
                Toast.makeText(this, "you clicked on exit", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:

                break;
        }
        return true;
    }

    private void showHistory() {
        // Get history data (replace this with your actual history data)
        String historyData = readAllHistoryFiles();

        // Start HistoryActivity and pass history data
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("history_data", historyData);
        startActivity(intent);
    }

    private String readAllHistoryFiles() {
        File directory = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "RESULT");
        }
        StringBuilder stringBuilder = new StringBuilder();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    stringBuilder.append("File: ").append(file.getName()).append("\n");
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringBuilder.append("\n\n");
                }
            }
        }
        return stringBuilder.toString();
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        // Call your notification method here or any other actions you want to perform when the activity pauses
        showNotification();
    }*/

}