package com.diegoginko.rssreader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FeedEntry{
    var name : String = ""
    var artist : String = ""
    var releaseDate : String = ""
    var summary : String = ""
    var imageURL : String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            summary = $summary
            imageUrl = $imageURL
        """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"llamado a onCreate")
        val descargarDatos = DescargarDatos()
        val rutaRss : String = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml"
        descargarDatos.execute(rutaRss)
        Log.d(TAG,"Terminó el onCreate")
    }

    companion object {
        private class DescargarDatos : AsyncTask<String, Void, String>(){
            private val TAG : String = "DescargarDatos"

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "parametro de onPostExecute: $result")
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG,"doInBackground empieza con ${url[0]}")
                val rssFeed = descargarXML(url[0])
                if (rssFeed.isEmpty()){
                    Log.e(TAG,"Error, feed vacio en ${url[0]}")
                }
                return rssFeed
            }

            private fun descargarXML(direccionUrl : String?) : String{
//                val xmlResult = StringBuilder()
//
//                try {
//                    val url = URL(direccionUrl)
//                    val conexion : HttpURLConnection = url.openConnection() as HttpURLConnection
//                    val respuesta = conexion.responseCode
//                    Log.d(TAG, "descargarXML | Codigo de respuesta: $respuesta")
//
////                    val reader = BufferedReader(InputStreamReader(conexion.inputStream)) //Estas clases trabajan juntas asi que no tiene sentido ponerlas separadas
////
////                    //se leen en cadenas de char, por lo que hay que armar el string
////                    val inputBuffer = CharArray(500)
////                    var charsRead = 0
////                    while(charsRead <= 0){
////                        charsRead = reader.read(inputBuffer)
////                        if (charsRead > 0){
////                            xmlResult.append(String(inputBuffer, 0, charsRead))
////                        }
////                    }
////                    reader.close() //Cierro el reader que cierra lo que tiene anidado dentro
//
//                    val stream = conexion.inputStream
//                    stream.buffered().reader().use { reader ->
//                        xmlResult.append(reader.readText())
//                    } //Se reemplaza el codigo anterior por esto.
//
//
//                    return xmlResult.toString() //convierto el array de chars en string
//
//
////                }catch (e : MalformedURLException){
////                    Log.e(TAG, "descargarXML | URL invalida: ${e.message}")
////
////                }catch (e : IOException){
////                    Log.e(TAG, "descargarXML | IOExeption leyendo los datos: ${e.message}")
////                }
////                catch (e : SecurityException){
////                    Log.e(TAG, "descargarXML | Faltan permisos: ${e.message}")
////                }catch ( e : Exception){
////                    Log.e(TAG, "descargarXML | Exception no reconocida: ${e.message}")
////                }
//                }catch (e : Exception){
//                    val mensajeError :  String
//                    when(e){
//                        is MalformedURLException -> mensajeError = "descargarXML | URL invalida: ${e.message}"
//                        is IOException -> mensajeError = "descargarXML | IOExeption leyendo los datos: ${e.message}"
//                        is SecurityException -> mensajeError = "descargarXML | Faltan permisos: ${e.message}"
//                        else -> mensajeError = "descargarXML | Exception no reconocida: ${e.message}"
//                    }
//                    Log.e(TAG, mensajeError)
//                }
//
//                return ""  //esto solo lo retorna si entro en algun catch
                return URL(direccionUrl).readText()
            }
        }
    }


}
