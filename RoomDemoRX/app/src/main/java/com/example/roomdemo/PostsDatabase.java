package com.example.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Post.class,version = 1)
abstract class PostsDatabase extends RoomDatabase {
    static PostsDatabase instance;
    abstract PostsDao postsDao();

    static  PostsDatabase getInstance(Context context){
        if(instance==null){
           instance = Room.databaseBuilder(context.getApplicationContext(),
                   PostsDatabase.class,
                   "posts_database")
                   .fallbackToDestructiveMigration()
                   .build();
        }
        return instance;
    }
}
