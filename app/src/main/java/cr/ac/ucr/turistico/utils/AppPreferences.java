/**
 * La clase AppPreferences realiza las validacion para validar si el usuario se encuentra logueado
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    /**
     * Variables
     */
    private final String PREFERENCES = "primeraapp_preferences";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static AppPreferences preferences;

    /**
     * Clase Keys
     */
    public static class Keys{
        public static final String IS_LOGGED_IN = "is_logged_in";
        public static final String ITEMS= "items";
    }

    /**
     * Metodo Constructor
     * @param context
     */
    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Metodo getInstance
     * @param context
     * @return preferences
     */
    public static AppPreferences getInstance(Context context){
        if( preferences == null){
            preferences = new AppPreferences(context);
        }
        return preferences;
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key, String value){
        doEdit();
        editor.putString(key, value);
        commit();
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key,int value){
        doEdit();
        editor.putInt(key, value);
        commit();
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key,long value){
        doEdit();
        editor.putLong(key, value);
        commit();
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key,double value){
        doEdit();
        put(key, String.valueOf(value));
        commit();
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key,boolean value){
        doEdit();
        editor.putBoolean(key, value);
        commit();
    }
    /**
     * Metodo put
     * @param key
     * @param value
     */
    public void put(String key,float value){
        doEdit();
        editor.putFloat(key, value);
        commit();
    }
    /**
     * Metodo getString
     * @param key
     * @return
     */
    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }
    /**
     * Metodo getString
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue){
        return sharedPreferences.getString(key, defValue);
    }
    /**
     * Metodo getInt
     * @param key
     * @return
     */
    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }
    /**
     * Metodo getInt
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue){
        return sharedPreferences.getInt(key, defValue);
    }
    /**
     * Metodo getDouble
     * @param key
     * @return
     */
    public double getDouble(String key){
        try{
            return Double.valueOf(sharedPreferences.getString(key,String.valueOf(0)));
        } catch (NumberFormatException e){
            return 0;
        }
    }
    /**
     * Metodo getDouble
     * @param key
     * @param defValue
     * @return
     */
    public double getDouble(String key, double defValue){
        try{
            return Double.valueOf(sharedPreferences.getString(key, String.valueOf(defValue)));
        } catch (NumberFormatException e){
            return defValue;
        }
    }
    /**
     * Metodo getLong
     * @param key
     * @return
     */
    public long getLong(String key){
        return sharedPreferences.getLong(key,0);
    }
    /**
     * Metodo getLong
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue){
        return sharedPreferences.getLong(key,defValue);
    }
    /**
     * Metodo getFloat
     * @param key
     * @return
     */
    public float getFloat(String key){
        return sharedPreferences.getFloat(key,0);
    }
    /**
     * Metodo getFloat
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue){
        return sharedPreferences.getFloat(key,defValue);
    }
    /**
     * Metodo getBoolean
     * @param key
     * @return
     */
    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }
    /**
     * Metodo getBoolean
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue){
        return sharedPreferences.getBoolean(key,defValue);
    }
    /**
     * Metodo clear
     */
    public void clear(){
        doEdit();
        editor.clear();
        commit();
    }
    /**
     * Metodo remove
     * @param keys
     */
    public void remove(String ...keys){
        doEdit();

        for(String key: keys){
            editor.remove(key);
        }

        commit();
    }
    /**
     * Metodo doEdit
     */
    private void doEdit(){
        if(editor == null){
            editor = sharedPreferences.edit();
        }
    }
    /**
     * Metodo commit
     */
    private void commit(){
        if(editor != null){
            editor.commit();
            editor = null;
        }
    }
}
