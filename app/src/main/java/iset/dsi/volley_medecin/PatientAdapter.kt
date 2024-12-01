package iset.dsi.volley_medecin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PatientAdapter(
    private val context: android.content.Context,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Patient) -> Unit
) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {
    private var patients: List<Patient> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int = patients.size

    fun setPatients(patients: List<Patient>) {
        this.patients = patients
        notifyDataSetChanged()
    }

    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNom: TextView = itemView.findViewById(R.id.txtNom)
        private val txtPrenom: TextView = itemView.findViewById(R.id.txtPrenom)
        private val txtDateNaissance: TextView = itemView.findViewById(R.id.txtDateNaissance)
        private val txtAdresse: TextView = itemView.findViewById(R.id.txtAdresse)
        private val iconDelete: ImageView = itemView.findViewById(R.id.iconDelete)
        private val iconEdit: ImageView = itemView.findViewById(R.id.iconEdit)

        fun bind(patient: Patient) {
            txtNom.text = patient.nom
            txtPrenom.text = patient.prenom
            txtDateNaissance.text = patient.dateNaissance
            txtAdresse.text = patient.adresse

            // Gestion du clic sur l'icône de suppression
            iconDelete.setOnClickListener {
                onDelete(patient.id)
            }

            // Gestion du clic sur l'icône d'édition
            iconEdit.setOnClickListener {
                val intent = Intent(context, ModifyPatientActivity::class.java)
                intent.putExtra("patient_id", patient.id) // Passer l'ID du patient
                context.startActivity(intent)
            }
        }
    }
}
