package com.example.passwordstorage;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "password")
public class PassWord {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "AppName")
    private String AppName;
    @ColumnInfo(name = "Pass")
    private byte[] Pass;
    @ColumnInfo(name = "UserName")
    private String UserName;
    @ColumnInfo(name = "Category")
    private String Category;
    @ColumnInfo(name = "Lock")
    private Boolean Lock = false;
    @ColumnInfo(name = "Theme")
    private int Theme;

    public PassWord(String AppName, byte[] Pass, String UserName, String Category, int Theme){
        this.AppName = AppName;
        this.Pass = Pass;
        this.UserName = UserName;
        this.Category = Category;
        this.Theme = Theme;
    }

    public PassWord clone(){
        PassWord passWord = new PassWord(AppName,Pass,UserName,Category,Theme);
        passWord.setId(id);
        return passWord;
    }

    public int getId(){return id;}
    public String getAppName(){return AppName;}
    public byte[] getPass(){return Pass;}
    public String getUserName(){return UserName;}
    public String getCategory(){return Category;}
    public int getTheme(){return Theme;}
    public Boolean getLock(){return Lock;}

    public void setAppName(String AppName){this.AppName = AppName;}
    public void setId(int id){this.id = id;}
    public void setPass(byte[] Pass){this.Pass = Pass;}
    public void setCategory(String Category){this.Category = Category;}
    public void setLock(Boolean Lock){this.Lock = Lock;}
}
