package com.davf392.panierlocal.data.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

/**
 * Parser for raw responses returned by the Socleo API.
 * Socleo returns responses in key-value pairs formatted like query parameters:
 * "CODE_RETOUR=00000&SCEAU=...&FOURNISSEURS=[...]&PRODUITS=[...]"
 */
object SocleoResponseParser {

    /**
     * Splits a raw Socleo response into key-value pairs.
     * Respects nested JSON brackets `[` `]`, braces `{` `}`, and quotes `"`
     * so that nested '&' characters do not break parameter boundaries.
     */
    fun parseResponse(rawResponse: String): Map<String, String> {
        val trimmed = rawResponse.trim()
        if (trimmed.isEmpty()) return emptyMap()

        // If the entire response is already a JSON array or object
        if ((trimmed.startsWith("[") && trimmed.endsWith("]")) ||
            (trimmed.startsWith("{") && trimmed.endsWith("}"))
        ) {
            return emptyMap()
        }

        val result = mutableMapOf<String, String>()
        var keyStartIndex = 0
        var equalsIndex = -1
        var inQuotes = false
        var isEscaped = false
        var bracketDepth = 0
        var braceDepth = 0

        for (i in trimmed.indices) {
            val c = trimmed[i]
            if (isEscaped) {
                isEscaped = false
                continue
            }
            if (c == '\\') {
                isEscaped = true
                continue
            }
            if (c == '"') {
                inQuotes = !inQuotes
                continue
            }
            if (!inQuotes) {
                when (c) {
                    '[' -> bracketDepth++
                    ']' -> if (bracketDepth > 0) bracketDepth--
                    '{' -> braceDepth++
                    '}' -> if (braceDepth > 0) braceDepth--
                    '=' -> {
                        if (bracketDepth == 0 && braceDepth == 0 && equalsIndex == -1) {
                            equalsIndex = i
                        }
                    }
                    '&' -> {
                        if (bracketDepth == 0 && braceDepth == 0) {
                            if (equalsIndex != -1) {
                                val key = trimmed.substring(keyStartIndex, equalsIndex).trim()
                                val value = trimmed.substring(equalsIndex + 1, i).trim()
                                if (key.isNotEmpty()) {
                                    result[key] = value
                                }
                            }
                            keyStartIndex = i + 1
                            equalsIndex = -1
                        }
                    }
                }
            }
        }

        // Process last pair
        if (equalsIndex != -1 && keyStartIndex < trimmed.length) {
            val key = trimmed.substring(keyStartIndex, equalsIndex).trim()
            val value = trimmed.substring(equalsIndex + 1).trim()
            if (key.isNotEmpty()) {
                result[key] = value
            }
        }

        return result
    }

    /**
     * Parses a JSON string representing an array of objects into a list of key-value maps.
     */
    fun parseJsonArrayToMapList(
        jsonString: String,
        json: Json = Json { ignoreUnknownKeys = true }
    ): List<Map<String, String>> {
        val trimmed = jsonString.trim()
        if (trimmed.isEmpty()) return emptyList()

        return try {
            val element = json.parseToJsonElement(trimmed)
            if (element is JsonArray) {
                element.mapNotNull { item ->
                    if (item is JsonObject) {
                        item.entries.associate { (key, value) ->
                            key to if (value is JsonPrimitive) (value.contentOrNull ?: "") else value.toString()
                        }
                    } else null
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
