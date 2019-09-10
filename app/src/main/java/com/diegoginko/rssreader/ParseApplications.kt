package com.diegoginko.rssreader

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    private val TAG = "ParseApplications"
    val applications : ArrayList<FeedEntry> = ArrayList<FeedEntry>()

    fun parse(xmlData : String) : Boolean{
        Log.d(TAG,"parse called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT){
                val tagName = xpp.name.toLowerCase()  // TODO: Cambiar a operador safe-call
                when(eventType){

                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "Tag de inicio " + tagName)
                        if (tagName == "entry"){
                            inEntry = true
                        }
                    }

                    XmlPullParser.TEXT -> xpp.text

                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "Fin del tag " + tagName)
                        if (inEntry){  //Si es el fin del entry actual
                            when(tagName){

                                "entry" -> {
                                    applications.add(currentRecord) //Agrego el objeto del entry completo
                                    inEntry = false //Pongo en false para que salga
                                    currentRecord = FeedEntry() //reinicio el objeto
                                }

                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "releaseDate" -> currentRecord.releaseDate = textValue
                                "summary" -> currentRecord.summary = textValue
                                "image" -> currentRecord.imageURL = textValue
                            }
                        }

                    }

                }

                //Nada mas que hacer
                eventType = xpp.next()
            }


        }catch (e: Exception){
            e.printStackTrace()
            status = false
        }

        return status
    }

}