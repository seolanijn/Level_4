package com.example.seolanjin.seolanjinproject1;

public class Record {
    public int _id;
    public String _date, _time, _dogName;

    public Record(int id, String date, String time, String dogName ){
        this._id = id;
        this._date = date;
        this._time = time;
        this._dogName = dogName;
    }


    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getsDate() {
        return _date;
    }

    public void setsDate(String date) {
        this._date = date;
    }

    public String getTime() {
        return _time;
    }

    public void setsTime(String time) {
        this._time = time;
    }

    public String getDogName() {
        return _dogName;
    }

    public void setDogName(String dogName) {
        this._dogName = dogName;
    }

}
