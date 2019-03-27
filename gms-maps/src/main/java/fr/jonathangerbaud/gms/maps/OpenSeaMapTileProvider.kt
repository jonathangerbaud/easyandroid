package fr.jonathangerbaud.gms.maps

import java.net.URL

class OpenSeaMapTileProvider : OfflineUrlTileProvider(256, 256, "openseamap")
{
    override fun getTileUrl(x: Int, y: Int, z: Int): URL {
        return URL("http://t1.openseamap.org/seamark/%d/%d/%d.png".format(z, x, y))

//        Log.d("OSMTileProvideor", "http://alpha.openseamap.org/tiles/base/%d/%d/%d.png".format(z, x, y))
//        return URL("http://alpha.openseamap.org/tiles/base/%d/%d/%d.png".format(z, x, y))
    }

}