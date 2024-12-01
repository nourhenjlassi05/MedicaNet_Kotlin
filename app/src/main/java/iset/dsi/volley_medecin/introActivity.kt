package iset.dsi.volley_medecin



import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import iset.dsi.volley_medecin.databinding.ActivityIntroBinding

class introActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(binding.root)


        binding.apply {
            startBtn.setOnClickListener {
                // Lancement de l'activité MainActivity
                startActivity(Intent(this@introActivity, LoginActivity::class.java))
            }

        }

    }
}