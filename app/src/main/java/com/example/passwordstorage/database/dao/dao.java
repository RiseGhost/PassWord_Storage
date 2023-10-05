package com.example.passwordstorage.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.passwordstorage.PassWord;

import java.util.List;

@Dao
public interface dao {
    @Insert
    void Insert(PassWord passWord);
    @Delete
    void Remove(PassWord passWord);
    @Query("SELECT * FROM password")
    List<PassWord> getAll();
}
