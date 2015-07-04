package com.cablevision.cambaceomovil.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cablevision.cambaceomovil.dto.Usuario;

/**
 * Created by Mike on 7/3/15.
 */
public class UsuarioAlmacenLocal {
    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UsuarioAlmacenLocal(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(Usuario user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("nombre", user.getNombre());
        userLocalDatabaseEditor.putString("username", user.getUsername());
        userLocalDatabaseEditor.putString("password", user.getPassword());
        userLocalDatabaseEditor.putString("token", user.getSessionToken());
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public Usuario getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String name = userLocalDatabase.getString("nombre", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String token = userLocalDatabase.getString("token", "");

        Usuario user = new Usuario(name, username, password, token);
        return user;
    }
}
