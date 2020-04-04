package com.mrmi.mealsuggestor

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit() //Edit shared preferences to store data

        //Initializer string to avoid erros when displaying items from empty preferences
        if(!sharedPreferences.contains("initializer"))
        {
            editor.putString("initializer", "initializer")
            editor.apply()
        }


        var currentItem = ""
        displayText.text="You should eat at: "

        displayButton.setOnClickListener()
        {
            try
            {
                val items = sharedPreferences.getAll() //Load all items stored in preferences
                if(items.count()>1)
                {
                    val foodList = arrayListOf("") //Create empty foodList to pick an item from
                    for(item in items) //Loop through all items in the preferences
                    {
                        val toShow = item.value.toString()
                        if(toShow!="" && toShow!="initializer")
                        {
                            println("item: " + toShow)
                            foodList.add(toShow) //Add all items from preferences to foodList
                        }
                    }
                    //Pick and display a random item from foodList
                    val random = Random()
                    var randomFood = random.nextInt(foodList.count())
                    //Extra check to make sure food item isn't empty
                    while(foodList[randomFood]=="")
                    {
                        randomFood = random.nextInt(foodList.count())
                    }
                    displayText.text = "You should eat at: " + foodList[randomFood]
                    currentItem = foodList[randomFood]
                }
            }
            catch(e: Exception)
            {
                println("Display Error")
            }
        }

        enterButton.setOnClickListener()
        {
            val toAdd = inputText.text.toString()
            if(toAdd!="")
            {
                try
                {
                    if(!sharedPreferences.contains(toAdd)) //If the item doesn't exist in the preferences, input it
                    {
                        editor.putString(toAdd, toAdd) //Put data in shared preferences as a (key, value) pair
                        editor.apply() //Save changes
                    }

                }
                catch(e: Exception)
                {
                    println("Insertion error")
                }
            }
        }

        deleteButton.setOnClickListener()
        {
            try
            {
                editor.remove(currentItem).apply() //Remove item withk key currentItem
                displayText.text = "You should eat at: "
            }
            catch(e: Exception)
            {
                println("Deletion error")
            }
        }
    }
}
