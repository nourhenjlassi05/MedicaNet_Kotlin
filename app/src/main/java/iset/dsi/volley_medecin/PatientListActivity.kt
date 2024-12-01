package iset.dsi.volley_medecin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class PatientListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)

        recyclerView = findViewById(R.id.recyclerViewPatients)
        patientAdapter = PatientAdapter(
            context = this,
            onDelete = { patientId -> deletePatient(patientId) },
            onEdit = { /* Plus besoin ici, géré directement dans l'Adapter */ }
        )


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = patientAdapter

        loadPatients()

        val btnAddPatient: Button = findViewById(R.id.btnAddPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // Si le résultat est OK, rechargez la liste des patients
            loadPatients()
        }
    }

    private fun loadPatients() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/patients"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val patients = mutableListOf<Patient>()
                for (i in 0 until response.length()) {
                    val patientJson = response.getJSONObject(i)
                    val patient = Patient(
                        patientJson.getInt("id"),
                        patientJson.getString("nom"),
                        patientJson.getString("prenom"),
                        patientJson.getString("date_naissance"),
                        patientJson.getString("adresse")
                    )
                    patients.add(patient)
                }
                patientAdapter.setPatients(patients)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erreur: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }


    private fun deletePatient(patientId: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/patients/$patientId"

        val request = JsonObjectRequest(
            Request.Method.DELETE, url, null,
            Response.Listener {
                Toast.makeText(this, "Patient supprimé avec succès", Toast.LENGTH_SHORT).show()
                loadPatients() // Recharger la liste des patients
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erreur: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}