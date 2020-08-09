package com.example.services.Model

class Post
{
    private var postid: String = ""
    private var description: String = ""
    private var location: String = ""
    private var postimage: String = ""
    private var publisher: String = ""
    private var title: String = ""

  constructor()


    constructor(
        postid: String,
        description: String,
        location: String,
        postimage: String,
        publisher: String,
        title: String
    )
    {
        this.postid = postid
        this.description = description
        this.location = location
        this.postimage = postimage
        this.publisher = publisher
        this.title = title
    }

    fun getPostid(): String{
        return postid
    }
    fun getDescription(): String{
        return description
    }
    fun getPostimage(): String{
        return postimage
    }
    fun getLocation(): String{
        return location
    }
    fun getPublisher(): String{
        return publisher
    }
    fun getTitle(): String{
        return title
    }

    fun setPostid(postid: String)
    {
        this.postid = postid
    }
    fun setDescription(description: String)
    {
        this.description = description
    }
    fun setPostimage(postimage: String)
    {
        this.postimage = postimage
    }
    fun setLocation(location: String)
    {
        this.location = location
    }
    fun setPublisher(publisher: String)
    {
        this.publisher = publisher
    }
    fun setTitle(title: String)
    {
        this.title = title
    }

}