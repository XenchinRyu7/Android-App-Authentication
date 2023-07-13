package com.vsga.aplikasivalidasilogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME = "login";
    private EditText etUsername;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btLogin = findViewById(R.id.btLogin);
        Button btRegister = findViewById(R.id.btRegister);
        //event handler
        btLogin.setOnClickListener(v -> login());
        btRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    void simpanFileLogin() {
        String isiFile = etUsername.getText().toString() + ";" + etPassword.getText().toString();
        File file = new File(getFilesDir(), FILENAME);
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(isiFile.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
//        onBackPressed();
    }
    void login() {
        File appDir = getFilesDir();
        File file = new File(appDir, etUsername.getText().toString());

        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();

            String[] dataUser = data.split(";");
            if (dataUser[1].equals(etPassword.getText().toString())) {
                simpanFileLogin();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Password Tidak Sesuai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("close confirmation")
                .setMessage("close this app?")
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setCancelable(true)
                .setPositiveButton("YES", (dialog, which) -> finish())
                .setNegativeButton("NO", null).show();
    }
}