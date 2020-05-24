package net.pilsfree.covidstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recycler
import kotlinx.android.synthetic.main.activity_stats.*
import net.pilsfree.covidstats.adapter.StatsAdapter
import net.pilsfree.covidstats.model.Stat
import net.pilsfree.covidstats.retrofit.DataService
import net.pilsfree.covidstats.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val scroll = savedInstanceState?.getInt("scroll",0)?:0

        val service = RetrofitClientInstance.retrofit.create(DataService::class.java)

        val iso2 = intent.getStringExtra("ISO2")
        val slug = intent.getStringExtra("Slug")
        val country = intent.getStringExtra("Country")

        title = country
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        service.getCountryStats(slug).enqueue(object: Callback<List<Stat>> {
            override fun onFailure(call: Call<List<Stat>>, t: Throwable) {
                Toast.makeText(this@StatsActivity,"Nepovedlo se načíst detail země", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Stat>>, response: Response<List<Stat>>) {
                val list = response.body()!!.sortedBy { it.Date }.reversed()

                if (list.isEmpty()) {
                    nodata.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }

                val adapter = StatsAdapter(list)

                recycler.adapter = adapter

                recycler.layoutManager = LinearLayoutManager(this@StatsActivity,LinearLayoutManager.VERTICAL,false)
                recycler.addItemDecoration(DividerItemDecoration(this@StatsActivity,LinearLayoutManager.VERTICAL))
                (recycler.layoutManager as LinearLayoutManager).scrollToPosition(scroll)

            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("scroll",(recycler.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }
}
