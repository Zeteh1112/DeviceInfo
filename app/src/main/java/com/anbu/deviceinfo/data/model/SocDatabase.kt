package com.anbu.deviceinfo.data.model

import android.content.Context
import com.anbu.deviceinfo.data.model.SocData
import org.json.JSONObject

object SocDatabase {

    private var socMap: Map<String, SocData> = emptyMap()

    fun loadDatabase(context: Context) {

        if (socMap.isNotEmpty()) return

        val jsonString = context.assets
            .open("soc_database.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)

        val tempMap = mutableMapOf<String, SocData>()

        jsonObject.keys().forEach { key ->
            val obj = jsonObject.getJSONObject(key)
            tempMap[key.lowercase()] =
                SocData(
                    name = obj.getString("name"),
                    brand = obj.getString("brand")
                )
        }

        socMap = tempMap
    }

    fun getSoc(hardware: String): SocData? {
        return socMap[hardware.lowercase()]
    }
}
