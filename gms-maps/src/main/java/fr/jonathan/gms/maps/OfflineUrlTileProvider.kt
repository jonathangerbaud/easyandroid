package fr.jonathan.gms.maps

import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import fr.jonathangerbaud.core.BaseApp
import java.io.*
import java.net.URL

abstract class OfflineUrlTileProvider(val width:Int, val height:Int, private val key:String) : TileProvider
{
    companion object
    {
        private val rootDir:File = BaseApp.instance.filesDir

        private fun getCacheDir(key: String): File
        {
            return File(rootDir, key + File.separator)
        }

        fun getFile(key:String, x: Int, y: Int, z: Int):File
        {
            return File(getCacheDir(key), x.toString() + File.separator + y + File.separator + z + ".png")
        }

        fun mkdirs(key:String, x: Int, y: Int)
        {
            File(getCacheDir(key), x.toString() + File.separator + y + File.separator).mkdirs()
        }
    }
    abstract fun getTileUrl(x: Int, y: Int, z: Int): URL

    override fun getTile(x: Int, y: Int, z: Int): Tile?
    {
        val url: URL? = this.getTileUrl(x, y, z)

        if (url == null)
        {
            return TileProvider.NO_TILE
        }
        else
        {
            val inputStream:InputStream
            var bytesOutputStream:ByteArrayOutputStream? = null
            var fileOutputStream:FileOutputStream? = null

            val file = getFile(key, x, y, z)

            if (file.exists())
                inputStream = file.inputStream()
            else
                inputStream = url.openStream()

            var tile: Tile? = null
            try
            {
                bytesOutputStream = ByteArrayOutputStream()
                val bytes = ByteArray(4096)

                var length: Int = inputStream.read(bytes)
                while (length != -1)
                {
                    bytesOutputStream.write(bytes, 0, length)
                    length = inputStream.read(bytes)
                }

                if (!file.exists())
                {
                    mkdirs(key, x, y)
                    file.createNewFile()
                    fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(bytesOutputStream.toByteArray())
                }

                tile = Tile(width, height, bytesOutputStream.toByteArray())
            }
            catch (exception: IOException)
            {
                exception.printStackTrace()
            }
            finally
            {
                bytesOutputStream?.close()
                fileOutputStream?.close()
            }

            return tile
        }
    }
}