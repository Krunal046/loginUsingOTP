package com.example.demo.model.otpVerify

data class Colleague(
    val caller_id: Int,
    val caller_registred: Boolean,
    val clg_ref_id: String,
    val prof_doc_verified: Boolean,
    val prof_interviewed: Boolean,
    val prof_registered: Boolean,
    val srv_prof_id: Int
)
