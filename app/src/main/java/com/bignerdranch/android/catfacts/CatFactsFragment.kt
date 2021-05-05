package com.bignerdranch.android.catfacts

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.catfacts.api.CatFactsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "CatFactsFragment"

class CatFactsFragment: Fragment() {
    private lateinit var catFactsViewModel: CatFactsViewModel
    private lateinit var factRecyclerView: RecyclerView
    private var adapter: FactAdapter? = FactAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catFactsViewModel = ViewModelProviders.of(this).get(CatFactsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cat_facts, container, false)

        factRecyclerView =
            view.findViewById(R.id.fact_recycler_view) as RecyclerView
        factRecyclerView.layoutManager = LinearLayoutManager(context)
        factRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catFactsViewModel.factItemLiveData.observe(
            viewLifecycleOwner,
            Observer { factItems ->
                //Log.d(TAG,"hAVE FACT ITEMS FROM VIEWMODEL $factItems")
                //Eventually, update data backing the recycler view
                factRecyclerView.adapter = FactAdapter(factItems)

            })
    }




    private class FactHolder(view: View)
        :RecyclerView.ViewHolder(view) {

        private lateinit var factItem: FactItem

        private val factTextView: TextView = itemView.findViewById(R.id.fact_title)

        fun bind(factItem: FactItem){
            this.factItem = factItem
            factTextView.text = this.factItem.fact

        }
        //val bindTitle: (CharSequence) -> Unit = itemTextView::setText


    }

    private inner class FactAdapter(var factItems: List<FactItem>)
        :RecyclerView.Adapter<FactHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : FactHolder {
                //return FactHolder(inflater.inflate(R.layout.fact_item,parent, false))
                val view = layoutInflater.inflate(R.layout.fact_item,parent,false)
                //val textView = TextView(parent.context)
                return FactHolder(view)
        }

        override fun getItemCount(): Int = factItems.size

        override fun onBindViewHolder(holder: FactHolder, position: Int) {
            val factItem = factItems[position]
            holder.itemView.contentDescription = "This is fact number" + "${position+1}." + "${factItem.fact}}"
            holder.bind(factItem)

            //holder.bindTitle(factItem.fact)
        }


    }


    companion object{
        fun newInstance() = CatFactsFragment()
    }
}