package com.example.passwordstorage.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.passwordstorage.PassWord;
import com.example.passwordstorage.database.dao.dao;

@Database(entities = {PassWord.class},version = 1, exportSchema = false)
public abstract class database extends RoomDatabase {
    public abstract dao getPassWordDao();
}
