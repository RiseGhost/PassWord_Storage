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
    @Query("SELECT * FROM password WHERE Category = 'Social'")
    List<PassWord> getSocial();
    @Query("SELECT * FROM password WHERE Category = 'Games'")
    List<PassWord> getGames();
    @Query("SELECT * FROM password WHERE Category = 'Web'")
    List<PassWord> getWeb();
    @Query("SELECT * FROM password WHERE Category = 'Other'")
    List<PassWord> getOther();
}
