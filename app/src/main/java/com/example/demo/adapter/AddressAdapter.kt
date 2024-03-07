package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.model.addressList.Address

class AddressAdapter(private var addressList: ArrayList<Address>) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = addressList[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        private val tvAddressDes: TextView = itemView.findViewById(R.id.tv_address_des)
        private val tvPinCode: TextView = itemView.findViewById(R.id.tv_pin_code)
        private val tvPinCodeDes: TextView = itemView.findViewById(R.id.tv_pin_code_des)
        private val tvLocality: TextView = itemView.findViewById(R.id.tv_locality)
        private val tvLocalityDes: TextView = itemView.findViewById(R.id.tv_locality_des)
        private val tvGoogleAddress: TextView = itemView.findViewById(R.id.tv_google_address)
        private val tvGoogleAddressDes: TextView = itemView.findViewById(R.id.tv_google_address_des)
        private val tvZone: TextView = itemView.findViewById(R.id.tv_zone)
        private val tvZoneDes: TextView = itemView.findViewById(R.id.tv_zone_des)
        private val tvState: TextView = itemView.findViewById(R.id.tv_state)
        private val tvStateDes: TextView = itemView.findViewById(R.id.tv_state_des)
        private val tvCity: TextView = itemView.findViewById(R.id.tv_city)
        private val tvCityDes: TextView = itemView.findViewById(R.id.tv_city_des)

        fun bind(address: Address) {
            tvAddress.text = "Address = "
            tvAddressDes.text = address.address

            tvPinCode.text = "Pin Code = "
            tvPinCodeDes.text = address.pincode

            tvLocality.text = "Locality = "
            tvLocalityDes.text = address.locality

            tvGoogleAddress.text = "Google Address = "
            tvGoogleAddressDes.text = address.google_address

            tvZone.text = "Zone = "
            tvZoneDes.text = address.zone

            tvState.text = "State = "
            tvStateDes.text = address.state

            tvCity.text = "City = "
            tvCityDes.text = address.city
        }
    }

    fun updateList(list:ArrayList<Address>){
        addressList = list
        notifyDataSetChanged()
    }
}
