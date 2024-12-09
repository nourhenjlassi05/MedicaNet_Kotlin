package iset.dsi.volley_medecin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        supportActionBar?.hide()

    }
}

