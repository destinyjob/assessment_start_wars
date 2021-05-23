package com.starwars.starwars
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starwars.starwars.Person
import com.starwars.starwars.R

class PersonsAdapter(var context: Context): RecyclerView.Adapter<PersonsAdapter.PersonViewHolder>() {

    /**
     * ViewHolderClass
     * **/
    class PersonViewHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
        fun bindView(person: Person){
            val tView: TextView = itemView.findViewById(R.id.personName)
            tView.text = person.name

        }
    }

    private var personsList = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false), context)
    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindView(personsList[position])

        /**
         * This gets called when the ViewHolder is clicked
         * **/
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogView: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.alert_dialog_view, null)

            val tvName : TextView = dialogView.findViewById(R.id.name)
            val tvGender : TextView = dialogView.findViewById(R.id.gender)
            val tvHeight : TextView = dialogView.findViewById(R.id.height)

            tvName.text = personsList[position].name
            tvGender.text = personsList[position].gender
            tvHeight.text = personsList[position].height

            builder.setView(dialogView)
            builder.create().show()

        }
    }

    /**s
     * Creates list of persons
     * and notifies the system that data has changed
     * **/
    fun create(listOfPeople: List<Person>){
        this.personsList = listOfPeople.toMutableList()
        notifyDataSetChanged()
    }

    /**
     * updates the list of persons anytime more data is fetched from the api
     * data gets added to the previous list
     * and notifies the system that data has changed once again
     * **/
    fun update(listOfPeople: List<Person>){
        this.personsList.addAll(listOfPeople)
        notifyDataSetChanged()
    }



}