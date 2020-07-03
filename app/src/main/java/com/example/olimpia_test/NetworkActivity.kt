package com.example.olimpia_test

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_network.*


class NetworkActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        networkCallback()
        bluetoothStatus()

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver);
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun networkCallback() {
        val networkCallback: NetworkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : NetworkCallback() {
            override fun onAvailable(network: Network) { // network available
                wifi.setImageDrawable(getDrawable(R.drawable.ic_wifi_black_24dp))
                wifiText.setText(R.string.on)
                wifiText.setTextColor(getColor(android.R.color.holo_green_dark))
            }

            override fun onLost(network: Network) { // network unavailable
                wifi.setImageDrawable(getDrawable(R.drawable.ic_signal_wifi_off_black_24dp))
                wifiText.setText(R.string.off)
                wifiText.setTextColor(getColor(android.R.color.holo_red_dark))

            }
        }

        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    private fun bluetoothStatus() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) { // Device does not support Bluetooth
        } else if (!mBluetoothAdapter.isEnabled) { // Bluetooth is not enabled :)
            bluetoothStatusIcon(false)
        } else { // Bluetooth is enabled
            bluetoothStatusIcon(true)
        }
    }

    private fun bluetoothStatusIcon(isOn: Boolean) {
        when (isOn) {
            true -> {
                bluetooth.setImageDrawable(resources.getDrawable(R.drawable.ic_bluetooth_black_24dp))
                bluetoothText.setText(R.string.on)
                bluetoothText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            }
            false -> {
                bluetooth.setImageDrawable(resources.getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp))
                bluetoothText.setText(R.string.off)
                bluetoothText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
        }

    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothAdapter.ACTION_STATE_CHANGED == action) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        bluetoothStatusIcon(false)
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        bluetoothStatusIcon(true)
                    }
                    BluetoothAdapter.STATE_ON -> {
                        bluetoothStatusIcon(true)
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        bluetoothStatusIcon(false)
                    }
                }
            }
        }
    }

    fun startNextActivity(view: View) {
        startActivity(Intent(this, SendDataActivity::class.java))
        finishAffinity()

    }


}
