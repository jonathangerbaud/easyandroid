package fr.jonathangerbaud.gms.maps

import java.net.URL

class OpenStreetMapTileProvider : OfflineUrlTileProvider(256, 256, "openstreetmap")
{
    override fun getTileUrl(x: Int, y: Int, z: Int): URL {
        return URL("http://tile.openstreetmap.org/%d/%d/%d.png".format(z, x, y))
    }
}