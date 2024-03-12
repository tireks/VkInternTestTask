package com.tirexmurina.vkinterntask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.tirexmurina.vkinterntask.screen.HomeFragmentDirections
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController get() = findNavController(R.id.mainDataContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openDetails(id: String){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
    }

    //todo сделать слайдинг и на нлавном экране


}