package com.starwars.starwars
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    private val personsAdapter: PersonsAdapter = PersonsAdapter(this)
    private val personsList = mutableListOf<Person>()
    private var API_URL: String = "https://swapi.dev/api/people/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Query Api
         * **/
        queryAPI(API_URL)

        /**
         * Initializations
         * **/
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val linearLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = personsAdapter
        personsAdapter.create(personsList)

        onScroll(recyclerView)


    }

    /**
     * To Handle Scroll Loading
     * **/
    fun onScroll(recyclerView : RecyclerView){
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                if(!recyclerView.canScrollVertically(1)){

                    /**
                     * ToDo: Find out why the "http" changes to "https"
                     * // This is a temporary fix
                     * **/
                    if(API_URL.startsWith("http"))
                        queryAPI("https:" + API_URL.split(":")[1])
                }
            }
        })
    }


    /**
     * Query API using Volley library
     * **/
    val RESULTS : String = "results"
    val NAME : String = "name"
    val GENDER : String = "gender"
    val HEIGHT : String = "height"
    val NEXT : String = "next"

    fun queryAPI(api_url: String){

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            api_url,
            {response ->

                val jsonObject = JSONObject(response)
                val persons: JSONArray = jsonObject.getJSONArray(RESULTS)
                var i = 0
                personsList.clear()
                while(i < persons.length()){

                    val person = Person(
                        persons.getJSONObject(i).getString(NAME),
                        persons.getJSONObject(i).getString(GENDER),
                        persons.getJSONObject(i).getString(HEIGHT)
                    )
                    personsList.add(person)
                    i++
                }
                personsAdapter.update(personsList)
                this.API_URL = jsonObject.getString(NEXT)

            },

            {error ->
                println(error)
            }
        )
        request.tag = "sw"
        queue?.add(request)

    }
}
