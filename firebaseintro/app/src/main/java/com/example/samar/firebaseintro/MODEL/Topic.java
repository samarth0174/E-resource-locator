package com.example.samar.firebaseintro.MODEL;

import java.io.Serializable;

public class Topic implements Serializable {
    public  String topicname;
    public  String topiccode;



    public  Topic()
    {

    }

    public Topic(String topicname, String topiccode) {
        this.topicname = topicname;
        this.topiccode = topiccode;
    }

    public String getTopicname() {

        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getTopiccode() {
        return topiccode;
    }

    public void setTopiccode(String topiccode) {
        this.topiccode = topiccode;
    }

    @Override
    public String toString() {
        return topicname;
    }


}
