package edu.northeastern.cs5500.starterbot.model;

import org.bson.types.ObjectId;

/** Default implementation of a generic data model from Prof. Alexander Lash. */
public interface Model {
    ObjectId getId();

    void setId(ObjectId id);
}
