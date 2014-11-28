package org.exoplatform.support;

/**
 * Created by exo on 11/27/14.
 */
public class EventBean {

    private String name;
    private String date;

    public  EventBean ( String name, String date)
    {
        this.name = name;
        this.date = date;
    }

    public  String getName(){
        return name;
    }


    public  String getDate(){
        return date;
    }


    public void setName (String name){
      this.name = name;
    }


    public void setDate (String date){
        this.date = date;
    }
}
