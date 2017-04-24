package com.ekant.justbiz;

/**
 * Created by ekant on 26/12/15.
 */
public class ModGlobalClass
{
    private static ModGlobalClass instance;

    private Boolean data;

    // Restrict the constructor from being instantiated
    private ModGlobalClass()
    {}

    public void setData(Boolean d){
        this.data=d;
    }
    public Boolean getData(){
        return this.data;
    }

    public static synchronized ModGlobalClass getInstance()
    {
        if(instance==null){
            instance=new ModGlobalClass();
        }
        return instance;
    }
}
