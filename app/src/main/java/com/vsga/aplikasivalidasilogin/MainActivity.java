package com.vsga.aplikasivalidasilogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etNamaLengkap;
    private EditText etAsalSekolah;
    private EditText etAlamat;
    //local
    public static final String FILENAME = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//views to object
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Halaman Depan");

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etAsalSekolah = findViewById(R.id.etAsalSekolah);
        etAlamat = findViewById(R.id.etAlamat);
        etUsername.setEnabled(false);
        etEmail.setEnabled(false);
        etNamaLengkap.setEnabled(false);
        etAsalSekolah.setEnabled(false);
        etAlamat.setEnabled(false);
        bacaFileLogin();
    }
    void bacaFileLogin() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);
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
            bacaDataUser(dataUser[0]);
        }
    }
    void bacaDataUser(String fileName) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, fileName);
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
            etUsername.setText(dataUser[0]);
            etEmail.setText(dataUser[2]);
            etNamaLengkap.setText(dataUser[3]);
            etAsalSekolah.setText(dataUser[4]);
            etAlamat.setText(dataUser[5]);
        } else
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miLogout)
            showLogoutDialog();
        return super.onOptionsItemSelected(item);
    }
    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists() && file.delete())
            System.out.println("file telah dihapus");
    }
    void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation Logout")
                .setMessage("Anda yakin ingin Logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("YES", (dialog, whichButton) -> {
                    hapusFile();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("NO", null).show();
    }
}