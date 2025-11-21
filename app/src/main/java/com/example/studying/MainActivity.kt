package com.example.studying

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var usermobilenumber: EditText
    private lateinit var usercategory: EditText
    private lateinit var savebutton: Button
    private lateinit var showcategories: Spinner
    private lateinit var filterbutton: Button
    private lateinit var showallbutton: Button
    private lateinit var showallcontacts: ListView

    private lateinit var db: ContactDatabase
    private lateinit var contactDao: ContactsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // now after we define the needed variables we will catch them from the xml
        username = findViewById(R.id.etName)
        usermobilenumber = findViewById(R.id.etPhone)
        usercategory = findViewById(R.id.etCategory)
        savebutton = findViewById(R.id.btnSave)
        showcategories = findViewById(R.id.spCategory)
        filterbutton = findViewById(R.id.btnFilter)
        showallbutton = findViewById(R.id.btnShowAll)
        showallcontacts = findViewById(R.id.lvContacts)

        // now lets initilize database object to fix it through all the rest code
        db = ContactDatabase.getInstance(this)
        contactDao = db.contactDao()

        // now we will fill the list and the spinnery with any old data was existed in the database
        showAllContacts()
        loadCategories()

        // first lets write the save button logic
        savebutton.setOnClickListener {//we will listen until user press the button
            //and then taking the data
            val name = username.text.toString()
            val phone = usermobilenumber.text.toString()
            val category = usercategory.text.toString()
       //just simple validation
            if (name.isEmpty()  phone.isEmpty()  category.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    //making object and then insert it into the database
                    contactDao.insert(Contact(name = name, phone = phone, category = category))
                    //clear the inputs
                    username.text.clear()
                    usermobilenumber.text.clear()
                    usercategory.text.clear()
                    //show it in the list and in the spinner too
                    showAllContacts()
                    loadCategories()
                }
            }
        }

        // second lets make the filter button logic
        filterbutton.setOnClickListener {//we will listen on it
            val selectedCategory = showcategories.selectedItem.toString()
            lifecycleScope.launch {
                val filtered = contactDao.getContactsByCategory(selectedCategory)
                val names = filtered.map { "${it.name} • ${it.category}" }
                //and then add it in the list adapter
                showallcontacts.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    names
                )
            }
        }
