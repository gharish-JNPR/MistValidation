package com.mist.mistvalidation.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.mist.android.ErrorType
import com.mist.android.IndoorLocationCallback
import com.mist.android.IndoorLocationManager.getInstance
import com.mist.android.MistMap
import com.mist.android.MistPoint
import com.mist.mistvalidation.databinding.FragmentMainBinding
import com.mist.mistvalidation.util.SharedPrefsUtils
import com.mist.mistvalidation.util.Utils
import java.util.regex.Pattern


class MainFragment : Fragment() {

    private var invitationSecret: String? = null

    private var prodOrgList: MutableList<String> = mutableListOf()
    private var stagOrgList: MutableList<String> = mutableListOf()
    private var euOrgList: MutableList<String> = mutableListOf()
    private var prodGcpOrgList: MutableList<String> = mutableListOf()
    private var stagGcpOrgList: MutableList<String> = mutableListOf()
    private var awsEastOrgList: MutableList<String> = mutableListOf()
    private var prodGcpCanadaOrgList: MutableList<String> = mutableListOf()
    private var prodAwsGovOrgList: MutableList<String> = mutableListOf()
    private var stagAwsGovOrgList: MutableList<String> = mutableListOf()
    private var prodAuOrgList: MutableList<String> = mutableListOf()
    private var prodGcpUkOrgList: MutableList<String> = mutableListOf()
    private var canaryUSOrgList: MutableList<String> = mutableListOf()
    private var ProdAwsUaeOrgList: MutableList<String> = mutableListOf()
    private var ProdGcpCentralOrgList: MutableList<String> = mutableListOf()


    private lateinit var binding : FragmentMainBinding
    private lateinit var prodOrgAdapter: ProdOrgAdapter // Adapter Instance
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.btnEnroll.setOnClickListener {
            scanBarCode()
        }
    }

    private fun initViews() {
        prodOrgAdapter = ProdOrgAdapter()
        //SharedPrefsUtils.writeString(activity, "PROD_LIST", Gson().toJson(prodOrgList))
        val prodList = SharedPrefsUtils.readString(activity, "PROD_LIST")
        val stagList = SharedPrefsUtils.readString(activity, "STAG_LIST")
        val euList = SharedPrefsUtils.readString(activity, "EU_LIST")
        val prodGcpList = SharedPrefsUtils.readString(activity, "PROD_GCP_LIST")
        val stagGcpList = SharedPrefsUtils.readString(activity, "STAG_GCP_LIST")
        val awsEastList = SharedPrefsUtils.readString(activity, "AWS_EAST_LIST")
        val prodGcpCanadaList = SharedPrefsUtils.readString(activity, "PROD_GCP_CANADA_LIST")
        val prodAwsGovList = SharedPrefsUtils.readString(activity, "PROD_AWS_GOV_LIST")
        val stagAwsGovList = SharedPrefsUtils.readString(activity, "STAG_AWS_GOV_LIST")
        val prodAuList = SharedPrefsUtils.readString(activity, "PROD_AU_LIST")
        val canaryUsList = SharedPrefsUtils.readString(activity, "CANARY_US_LIST")
        val prodGcpUkList = SharedPrefsUtils.readString(activity, "PROD_GCP_UK_LIST")
        val prodAwsUaeList = SharedPrefsUtils.readString(activity, "PROD_AWS_UAE_LIST")
        val prodGcpCentralList = SharedPrefsUtils.readString(activity, "PROD_GCP_CENTRAL_LIST")


        if (!prodList.isNullOrEmpty() && Utils.getListFromString(prodList) != null) {
            binding.txtProd.visibility = View.VISIBLE
            prodOrgList = Utils.getListFromString(prodList)
        } else {
            binding.txtProd.visibility = View.GONE
        }

        if (!stagList.isNullOrEmpty() && Utils.getListFromString(stagList) != null) {
            binding.txtStag.visibility = View.VISIBLE
            stagOrgList = Utils.getListFromString(stagList)
        } else {
            binding.txtStag.visibility = View.GONE
        }

        if (!euList.isNullOrEmpty() && Utils.getListFromString(euList) != null) {
            binding.txtEu.visibility = View.VISIBLE
            euOrgList = Utils.getListFromString(euList)
        } else {
            binding.txtEu.visibility = View.GONE
        }

        if (!prodGcpList.isNullOrEmpty() && Utils.getListFromString(prodGcpList) != null) {
            binding.txtGcpProd.visibility = View.VISIBLE
            prodGcpOrgList = Utils.getListFromString(prodGcpList)
        } else {
            binding.txtGcpProd.visibility = View.GONE
        }

        if (!stagGcpList.isNullOrEmpty() && Utils.getListFromString(stagGcpList) != null) {
            binding.txtGcpStag.visibility = View.VISIBLE
            stagGcpOrgList = Utils.getListFromString(stagGcpList)
        } else {
            binding.txtGcpStag.visibility = View.GONE
        }

        if (!awsEastList.isNullOrEmpty() && Utils.getListFromString(awsEastList) != null) {
            binding.txtAwsEast.visibility = View.VISIBLE
            awsEastOrgList = Utils.getListFromString(awsEastList)
        } else {
            binding.txtAwsEast.visibility = View.GONE
        }

        if (!prodGcpCanadaList.isNullOrEmpty() && Utils.getListFromString(prodGcpCanadaList) != null) {
            binding.txtGcpProdCanada.visibility = View.VISIBLE
            prodGcpCanadaOrgList = Utils.getListFromString(prodGcpCanadaList)
        } else {
            binding.txtGcpProdCanada.visibility = View.GONE
        }

        if (!prodAwsGovList.isNullOrEmpty() && Utils.getListFromString(prodAwsGovList) != null) {
            binding.txtAwsGovProd.visibility = View.VISIBLE
            prodAwsGovOrgList = Utils.getListFromString(prodAwsGovList)
        } else {
            binding.txtAwsGovProd.visibility = View.GONE
        }

        if (!stagAwsGovList.isNullOrEmpty() && Utils.getListFromString(stagAwsGovList) != null) {
            binding.txtAwsGovStag.visibility = View.VISIBLE
            stagAwsGovOrgList = Utils.getListFromString(stagAwsGovList)
        } else {
            binding.txtAwsGovStag.visibility = View.GONE
        }

        if (!prodAuList.isNullOrEmpty() && Utils.getListFromString(prodAuList) != null) {
            binding.txtAuProd.visibility = View.VISIBLE
            prodAuOrgList = Utils.getListFromString(prodAuList)
        } else {
            binding.txtAuProd.visibility = View.GONE
        }

        if (!canaryUsList.isNullOrEmpty() && Utils.getListFromString(canaryUsList) != null) {
            binding.txtCanaryUs.visibility = View.VISIBLE
            canaryUSOrgList = Utils.getListFromString(canaryUsList)
        } else {
            binding.txtCanaryUs.visibility = View.GONE
        }

        if (!prodGcpUkList.isNullOrEmpty() && Utils.getListFromString(prodGcpUkList) != null) {
            binding.txtUkGcpProd.visibility = View.VISIBLE
            prodGcpUkOrgList = Utils.getListFromString(prodGcpUkList)
        } else {
            binding.txtUkGcpProd.visibility = View.GONE
        }

        if (!prodAwsUaeList.isNullOrEmpty() && Utils.getListFromString(prodAwsUaeList) != null) {
            binding.txtAwsUaeProd.visibility = View.VISIBLE
            ProdAwsUaeOrgList = Utils.getListFromString(prodAwsUaeList)
        } else {
            binding.txtAwsUaeProd.visibility = View.GONE
        }

        if (!prodGcpCentralList.isNullOrEmpty() && Utils.getListFromString(prodGcpCentralList) != null) {
            binding.txtGcpCentralProd.visibility = View.VISIBLE
            ProdGcpCentralOrgList = Utils.getListFromString(prodGcpCentralList)
        } else {
            binding.txtGcpCentralProd.visibility = View.GONE
        }

        setUpAdapters()
    }

    private fun setUpAdapters() {

        binding.listProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listProd.adapter = prodOrgAdapter // Attach Adapter
        prodOrgAdapter.setOrdNames(prodOrgList)

    }

    companion object {
        var orgName: String? = null
    }

    private fun scanBarCode() {
        val integrator = IntentIntegrator(requireActivity())
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0)
        integrator.setBarcodeImageEnabled(true)

        val scanIntent = integrator.createScanIntent()
        qrCodeLauncher.launch(scanIntent)  // Use the new launcher
    }
    private val qrCodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        if (data != null) {
            val scanResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (scanResult != null) {
                if (scanResult.contents == null) {
                    Toast.makeText(requireContext(), "Cancelled Scan", Toast.LENGTH_LONG).show()
                } else {

                    val contents = data.getStringExtra("SCAN_RESULT")
                    val pattern = Pattern.compile("^http[s]?:\\/\\/.*\\/(.+)")

                    val matcher = pattern.matcher(contents ?: "")

                    var secret = ""

                    if (matcher.find()) {
                        secret = matcher.group(1) ?: ""
                    }

                    enroll(secret)
                }
            }
        } else {
            Toast.makeText(requireContext(), "No scan data received", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enroll(secret: String) {
        if (!TextUtils.isEmpty(secret)) {
            invitationSecret = secret
            getInstance(requireActivity(), secret).start(object : IndoorLocationCallback {
                override fun onRelativeLocationUpdated(relativeLocation: MistPoint?) {
                }

                override fun onMapUpdated(map: MistMap?) {
                }

                override fun onReceivedOrgInfo(tokenName: String?, orgID: String?) {
                    if (activity != null) activity!!.runOnUiThread {
                        val envType = invitationSecret!![0].toString()
                        if (tokenName != null) {
                            createOrgList(tokenName, envType)
                        }
                        orgName = tokenName
                        Handler(Looper.getMainLooper()).postDelayed({
                            getInstance(
                                activity!!, secret
                            ).stop()
                        }, 5000)
                    }
                }

                override fun onError(errorType: ErrorType, errorMessage: String) {

                }
            })
        }
    }

    private fun createOrgList(orgName: String, envType: String) {
        if (envType.equals("P", ignoreCase = true)) {
            if (SharedPrefsUtils.readString(activity, "PROD_LIST")?.let {
                    checkForDuplicates(it, orgName)
                } == true) {
                if (prodOrgList.isEmpty()) {
                    binding.txtProd.visibility = View.VISIBLE
                }
                prodOrgList.add(orgName)
                SharedPrefsUtils.writeString(activity, "PROD_LIST", Gson().toJson(prodOrgList))
                prodOrgAdapter.setOrdNames(prodOrgList)
            }
        }else if(envType.equals("S", ignoreCase = true)){
            if (SharedPrefsUtils.readString(activity, "STAG_LIST")?.let {
                    checkForDuplicates(it, orgName)
                } == true) {
                if (prodOrgList.isEmpty()) {
                    binding.txtProd.visibility = View.VISIBLE
                }
                prodOrgList.add(orgName)
                SharedPrefsUtils.writeString(activity, "STAG_LIST", Gson().toJson(prodOrgList))
                prodOrgAdapter.setOrdNames(prodOrgList)
            }
        }
    }

    private fun checkForDuplicates(listName : String, orgName: String): Boolean {
        var listToCheck = ArrayList<String>()
        if (Utils.getListFromString(listName) != null) 
            listToCheck = Utils.getListFromString(listName)

        return listToCheck != null && !listToCheck.contains(orgName)
    }



}