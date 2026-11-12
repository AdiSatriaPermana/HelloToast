package lat.pam.hellotoast// Sesuaikan dengan nama package project kamu

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Mengatur padding otomatis agar tidak tertutup status bar & navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + 32,
                systemBars.top + 16,
                systemBars.right + 32,
                systemBars.bottom + 16
            )
            insets
        }

        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonTakePhoto = findViewById<Button>(R.id.button_take_photo)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switch_page)

        // Observer LiveData: otomatis memperbarui TextView saat nilai berubah atau layar dirotasi
        val nameObserver = Observer<Int> { newCount ->
            mShowCount.text = newCount.toString()
        }
        model.currentName.observe(this, nameObserver)

        // Tombol Count menggunakan LiveData
        buttonCountUp.setOnClickListener {
            val current = model.currentName.value ?: 0
            model.currentName.value = current + 1
        }

        // Tombol Toast
        buttonToast.setOnClickListener {
            val angkaSekarang = model.currentName.value ?: 0
            Toast.makeText(this, "Angka saat ini: $angkaSekarang", Toast.LENGTH_SHORT).show()
        }

        // Tombol Take Photo (membuka kamera bawaan)
        buttonTakePhoto.setOnClickListener {
            val intentKamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intentKamera)
        }

        // Tombol Switch Page (pindah ke Activity kedua jika sudah ada)
        buttonSwitchPage.setOnClickListener {
            // Ganti SecondActivity::class.java dengan nama Activity kedua di project kamu jika ada
            // val intent = Intent(this, SecondActivity::class.java)
            // startActivity(intent)
            Toast.makeText(this, "Pindah halaman", Toast.LENGTH_SHORT).show()
        }
    }
}