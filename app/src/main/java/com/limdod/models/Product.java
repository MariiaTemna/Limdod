package com.limdod.models;

import java.io.Serializable;

public class Product implements Serializable
{
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String price;
    private String state;
    private String status;
    private User postedBy;

    public Product(String id, String name, String description, String imageUrl, String price, String state, String status)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.state = state;
        this.status = status;
    }

    public Product()
    {

    }

    public User getPostedBy()
    {
        return postedBy;
    }

    public void setPostedBy(User postedBy)
    {
        this.postedBy = postedBy;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
