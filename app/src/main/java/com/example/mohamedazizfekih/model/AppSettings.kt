package com.example.mohamedazizfekih.model

// Based on Lab 7.1 - Data class: groups the player settings in one object.
data class AppSettings(
    val useTimer: Boolean = true,
    val soundEnabled: Boolean = true,
    val hapticEnabled: Boolean = true
)
