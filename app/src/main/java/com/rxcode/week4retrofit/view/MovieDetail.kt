package com.rxcode.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rxcode.week4retrofit.adapter.GenreAdapter
import com.rxcode.week4retrofit.adapter.ProductionHouseAdapter
import com.rxcode.week4retrofit.adapter.ProductionCountryAdapter
import com.rxcode.week4retrofit.databinding.ActivityMovieDetailBinding
import com.rxcode.week4retrofit.helper.Const
import com.rxcode.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterProductionHouse: ProductionHouseAdapter
    private lateinit var adapterSpokenLanguage: ProductionCountryAdapter
    private lateinit var ViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val movieId = intent.getIntExtra("movie_id",0)
        Toast.makeText(applicationContext, "Movie ID: ${movieId}", Toast.LENGTH_SHORT).show()

        ViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        ViewModel.getMovieDetail(Const.API_KEY, movieId)
        binding.detailProgressBar.visibility=View.VISIBLE
        ViewModel.movieDetails.observe(this, Observer{
            response->

            binding.detailProgressBar.visibility=View.INVISIBLE
            //movie detail
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }

            //recycle view buat genre
            binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterGenre = GenreAdapter(response.genres)
            binding.rvGenre.adapter = adapterGenre



            //recycle view buat production house
            binding.rvProductionHouse.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterProductionHouse = ProductionHouseAdapter(response.production_companies)
            binding.rvProductionHouse.adapter = adapterProductionHouse

            binding.tvTitleMovieDetail.apply {
                text = response.title
            }

//            width, load, into (GLIDE digunakan untuk memunculkan foto)
            Glide.with(applicationContext)
                .load(Const.IMG_URL + response.backdrop_path)
                .into(binding.imgPosterMovie)


            // overview
            binding.tvOverview.apply {
                text = response.overview
            }

            //recycle view buat pproduction country
            binding.rvProductionCountry.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterSpokenLanguage = ProductionCountryAdapter(response.production_countries)
            binding.rvProductionCountry.adapter = adapterSpokenLanguage

//            binding.tvTitleMovieDetail.apply {
//                text = response.title
//            }


        })

    }
}