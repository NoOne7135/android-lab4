package com.example.holidaysapp.database

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Holiday(
    var date: String,
    var localName: String,
    var name: String,
    var countryCode: String,
    var isFixed: Boolean,
    var isGlobal: Boolean,
    var types: List<String>
){
    constructor() : this("", "", "", "", false, false, listOf())
}
