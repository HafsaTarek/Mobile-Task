package com.example.studying

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactsDao {
    @Insert
    suspend fun insert(contact: Contact)

    // filter all the contacts based on the input comming category
    @Query("SELECT * FROM contacts WHERE category = :category ")
    suspend fun getContactsByCategory(category: String): List<Contact>

    // get all the conatcts
    @Query("SELECT * FROM contacts ")
    suspend fun getAllContacts(): List<Contact>
    //getting all the distinct categories from the database to appear them in the spinner
    @Query("SELECT DISTINCT category FROM contacts ")
    suspend fun getAllUniqueCategries(): List<String>

}