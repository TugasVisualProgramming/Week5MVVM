package com.rxcode.week4retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxcode.week4retrofit.model.MovieDetails
import com.rxcode.week4retrofit.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.rxcode.week4retrofit.model.Result
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(private val respository:MoviesRepository): ViewModel(){

//    Get Now Playing Data
    val _nowPlaying: MutableLiveData<ArrayList<Result>> by lazy{
        MutableLiveData<ArrayList<Result>>()
    }

    val nowPlaying: LiveData<ArrayList<Result>>
    get() = _nowPlaying

    fun getNowPlaying(apikey: String, language: String, page: Int) = viewModelScope.launch{
        respository.getNowPlayingResults(apikey, language,page).let{
            response->
            if(response.isSuccessful){
                _nowPlaying.postValue(response.body()?.results as
                ArrayList<Result>?)
            }else{
                Log.e("Get Data", "Failed")
            }
        }
    }

//    Get Movie Details
    val _movieDetails: MutableLiveData<MovieDetails> by lazy{
        MutableLiveData<MovieDetails>()
    }

    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    fun getMovieDetail(apikey: String, movieId: Int) = viewModelScope.launch{
        respository.getMovieDetailResults(apikey, movieId).let{
            response->
            if(response.isSuccessful){
                _movieDetails.postValue(response.body() as MovieDetails)
            }else{
                Log.e("Get Data", "Failed")
            }
        }
    }
}