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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.mist.android.ErrorType
import com.mist.android.IndoorLocationCallback
import com.mist.android.IndoorLocationManager.getInstance
import com.mist.android.MistMap
import com.mist.android.MistPoint
import com.mist.mistvalidation.databinding.FragmentMainBinding
import com.mist.mistvalidation.util.Utils
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class MainFragment : Fragment() {
    private val viewModel: MainFragmentViewModel by viewModels()

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
    private var prodAwsUaeOrgList: MutableList<String> = mutableListOf()
    private var prodGcpCentralOrgList: MutableList<String> = mutableListOf()


    private lateinit var binding : FragmentMainBinding

    private val prodOrgAdapter: OrgAdapter = OrgAdapter()
    private val stagOrgAdapter: OrgAdapter = OrgAdapter()
    private val euOrgAdapter: OrgAdapter = OrgAdapter()
    private val stagGcpOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodGcpOrgAdapter: OrgAdapter = OrgAdapter()
    private val awsEastOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodGcpCanadaOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodAwsGovOrgAdapter: OrgAdapter = OrgAdapter()
    private val stagAwsGovOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodAuOrgAdapter: OrgAdapter = OrgAdapter()
    private val canaryUsOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodGcpUkOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodAwsUaeOrgAdapter: OrgAdapter = OrgAdapter()
    private val prodGcpCentralOrgAdapter: OrgAdapter = OrgAdapter()

    private var prodList: String? = ""
    private var stagList: String? = ""
    private var euList: String? = ""
    private var prodGcpList: String? = ""
    private var stagGcpList: String? = ""
    private var awsEastList: String? = ""
    private var prodGcpCanadaList: String? = ""
    private var prodAwsGovList: String? = ""
    private var stagAwsGovList: String? = ""
    private var prodAuList: String? = ""
    private var canaryUsList: String? = ""
    private var prodGcpUkList: String? = ""
    private var prodAwsUaeList: String? = ""
    private var prodGcpCentralList: String? = ""

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
        lifecycleScope.launch {
            prodList = viewModel.getList("PROD_LIST") ?: ""
            stagList = viewModel.getList("STAG_LIST") ?: ""
            euList = viewModel.getList("EU_LIST") ?: ""
            prodGcpList = viewModel.getList("PROD_GCP_LIST") ?: ""
            stagGcpList = viewModel.getList("STAG_GCP_LIST") ?: ""
            awsEastList = viewModel.getList("AWS_EAST_LIST") ?: ""
            prodGcpCanadaList = viewModel.getList("PROD_GCP_CANADA_LIST") ?: ""
            prodAwsGovList = viewModel.getList("PROD_AWS_GOV_LIST") ?: ""
            stagAwsGovList = viewModel.getList("STAG_AWS_GOV_LIST") ?: ""
            prodAuList = viewModel.getList("PROD_AU_LIST") ?: ""
            canaryUsList = viewModel.getList("CANARY_US_LIST") ?: ""
            prodGcpUkList = viewModel.getList("PROD_GCP_UK_LIST") ?: ""
            prodAwsUaeList = viewModel.getList("PROD_AWS_UAE_LIST") ?: ""
            prodGcpCentralList = viewModel.getList("PROD_GCP_CENTRAL_LIST") ?: ""

            updateUi()
            setUpAdapters()
        }
    }
    private fun updateUi(){
        if (prodList!="") {
            binding.txtProd.visibility = View.VISIBLE
            prodOrgList = Utils.getListFromString(prodList)
        } else {
            binding.txtProd.visibility = View.GONE
        }

        if (stagList!="") {
            binding.txtStag.visibility = View.VISIBLE
            stagOrgList = Utils.getListFromString(stagList)
        } else {
            binding.txtStag.visibility = View.GONE
        }

        if (euList!="") {
            binding.txtEu.visibility = View.VISIBLE
            euOrgList = Utils.getListFromString(euList)
        } else {
            binding.txtEu.visibility = View.GONE
        }

        if (prodGcpList!="") {
            binding.txtGcpProd.visibility = View.VISIBLE
            prodGcpOrgList = Utils.getListFromString(prodGcpList)
        } else {
            binding.txtGcpProd.visibility = View.GONE
        }

        if (stagGcpList!="") {
            binding.txtGcpStag.visibility = View.VISIBLE
            stagGcpOrgList = Utils.getListFromString(stagGcpList)
        } else {
            binding.txtGcpStag.visibility = View.GONE
        }

        if (awsEastList!="") {
            binding.txtAwsEast.visibility = View.VISIBLE
            awsEastOrgList = Utils.getListFromString(awsEastList)
        } else {
            binding.txtAwsEast.visibility = View.GONE
        }

        if (prodGcpCanadaList!="") {
            binding.txtGcpProdCanada.visibility = View.VISIBLE
            prodGcpCanadaOrgList = Utils.getListFromString(prodGcpCanadaList)
        } else {
            binding.txtGcpProdCanada.visibility = View.GONE
        }

        if (prodAwsGovList!="") {
            binding.txtAwsGovProd.visibility = View.VISIBLE
            prodAwsGovOrgList = Utils.getListFromString(prodAwsGovList)
        } else {
            binding.txtAwsGovProd.visibility = View.GONE
        }

        if (stagAwsGovList!="") {
            binding.txtAwsGovStag.visibility = View.VISIBLE
            stagAwsGovOrgList = Utils.getListFromString(stagAwsGovList)
        } else {
            binding.txtAwsGovStag.visibility = View.GONE
        }

        if (prodAuList!="") {
            binding.txtAuProd.visibility = View.VISIBLE
            prodAuOrgList = Utils.getListFromString(prodAuList)
        } else {
            binding.txtAuProd.visibility = View.GONE
        }

        if (canaryUsList!="") {
            binding.txtCanaryUs.visibility = View.VISIBLE
            canaryUSOrgList = Utils.getListFromString(canaryUsList)
        } else {
            binding.txtCanaryUs.visibility = View.GONE
        }

        if (prodGcpUkList!="") {
            binding.txtUkGcpProd.visibility = View.VISIBLE
            prodGcpUkOrgList = Utils.getListFromString(prodGcpUkList)
        } else {
            binding.txtUkGcpProd.visibility = View.GONE
        }

        if (prodAwsUaeList!="") {
            binding.txtAwsUaeProd.visibility = View.VISIBLE
            prodAwsUaeOrgList = Utils.getListFromString(prodAwsUaeList)
        } else {
            binding.txtAwsUaeProd.visibility = View.GONE
        }

        if (prodGcpCentralList!="") {
            binding.txtGcpCentralProd.visibility = View.VISIBLE
            prodGcpCentralOrgList = Utils.getListFromString(prodGcpCentralList)
        } else {
            binding.txtGcpCentralProd.visibility = View.GONE
        }
    }

    private fun setUpAdapters() {

        binding.listProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listProd.adapter = prodOrgAdapter // Attach Adapter
        prodOrgAdapter.setOrdNames(prodOrgList)

        binding.listStag.layoutManager = LinearLayoutManager(requireContext())
        binding.listStag.adapter = stagOrgAdapter // Attach Adapter
        stagOrgAdapter.setOrdNames(stagOrgList)

        binding.listEu.layoutManager = LinearLayoutManager(requireContext())
        binding.listEu.adapter = euOrgAdapter // Attach Adapter
        euOrgAdapter.setOrdNames(euOrgList)

        binding.listGcpStag.layoutManager = LinearLayoutManager(requireContext())
        binding.listGcpStag.adapter = stagGcpOrgAdapter // Attach Adapter
        stagGcpOrgAdapter.setOrdNames(stagGcpOrgList)

        binding.listGcpProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listGcpProd.adapter = prodGcpOrgAdapter // Attach Adapter
        prodGcpOrgAdapter.setOrdNames(prodGcpOrgList)

        binding.listAwsEast.layoutManager = LinearLayoutManager(requireContext())
        binding.listAwsEast.adapter = awsEastOrgAdapter // Attach Adapter
        awsEastOrgAdapter.setOrdNames(awsEastOrgList)

        binding.listGcpProdCanada.layoutManager = LinearLayoutManager(requireContext())
        binding.listGcpProdCanada.adapter = prodGcpCanadaOrgAdapter // Attach Adapter
        prodGcpCanadaOrgAdapter.setOrdNames(prodGcpCanadaOrgList)

        binding.listAwsGovProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listAwsGovProd.adapter = prodAwsGovOrgAdapter // Attach Adapter
        prodAwsGovOrgAdapter.setOrdNames(prodAwsGovOrgList)

        binding.listAwsGovStag.layoutManager = LinearLayoutManager(requireContext())
        binding.listAwsGovStag.adapter = stagAwsGovOrgAdapter // Attach Adapter
        stagAwsGovOrgAdapter.setOrdNames(stagAwsGovOrgList)

        binding.listAuProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listAuProd.adapter = prodAuOrgAdapter // Attach Adapter
        prodAuOrgAdapter.setOrdNames(prodAuOrgList)

        binding.listCanaryUs.layoutManager = LinearLayoutManager(requireContext())
        binding.listCanaryUs.adapter = canaryUsOrgAdapter // Attach Adapter
        canaryUsOrgAdapter.setOrdNames(canaryUSOrgList)

        binding.listUkGcpProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listUkGcpProd.adapter = prodGcpUkOrgAdapter // Attach Adapter
        prodGcpUkOrgAdapter.setOrdNames(prodGcpUkOrgList)

        binding.listUaeAwsProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listUaeAwsProd.adapter = prodAwsUaeOrgAdapter // Attach Adapter
        prodAwsUaeOrgAdapter.setOrdNames(prodAwsUaeOrgList)

        binding.listGcpCentralProd.layoutManager = LinearLayoutManager(requireContext())
        binding.listGcpCentralProd.adapter = prodGcpCentralOrgAdapter // Attach Adapter
        prodGcpCentralOrgAdapter.setOrdNames(prodGcpCentralOrgList)
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

                    //scan result gives us the text which is embedded in the QR code.
                    val contents = data.getStringExtra("SCAN_RESULT")
                    //this is a regular expression which is used for extract the last part of in the URL
                    val pattern = Pattern.compile("^https?://.*/(.+)")
                    //val pattern = Pattern.compile("^http[s]?:\\/\\/.*\\/(.+)")
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

        if (envType == "P") {
            lifecycleScope.launch {
                prodList = viewModel.getList("PROD_LIST") ?: ""
                if (prodList=="" || checkForDuplicates(prodList!!, orgName)) {
                    if (prodOrgList.isEmpty()) {
                        binding.txtProd.visibility = View.VISIBLE
                    }
                    prodOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_LIST",Gson().toJson(prodOrgList))
                    }
                    prodOrgAdapter.setOrdNames(prodOrgList)
                }
            }
        } else if(envType == "S"){
            lifecycleScope.launch {
                stagList = viewModel.getList("STAG_LIST") ?: ""
                if (stagList=="" || checkForDuplicates(stagList!!, orgName)) {
                    if (stagOrgList.isEmpty()) {
                        binding.txtStag.visibility = View.VISIBLE
                    }
                    stagOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("STAG_LIST",Gson().toJson(stagOrgList))
                    }
                    stagOrgAdapter.setOrdNames(stagOrgList)
                }
            }
        } else if(envType == "E"){
            lifecycleScope.launch {
                euList = viewModel.getList("EU_LIST") ?: ""
                if (euList=="" || checkForDuplicates(euList!!, orgName)) {
                    if (euOrgList.isEmpty()) {
                        binding.txtEu.visibility = View.VISIBLE
                    }
                    euOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("EU_LIST",Gson().toJson(euOrgList))
                    }
                    euOrgAdapter.setOrdNames(euOrgList)
                }
            }
        } else if(envType == "g"){
            lifecycleScope.launch {
                stagGcpList = viewModel.getList("STAG_GCP_LIST") ?: ""
                if (stagGcpList=="" || checkForDuplicates(stagGcpList!!, orgName)) {
                    if (stagGcpOrgList.isEmpty()) {
                        binding.txtGcpStag.visibility = View.VISIBLE
                    }
                    stagGcpOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("STAG_GCP_LIST",Gson().toJson(stagGcpOrgList))
                    }
                    stagGcpOrgAdapter.setOrdNames(stagGcpOrgList)
                }
            }
        } else if(envType == "G"){
            lifecycleScope.launch {
                prodGcpList = viewModel.getList("PROD_GCP_LIST") ?: ""
                if (prodGcpList=="" || checkForDuplicates(prodGcpList!!, orgName)) {
                    if (prodGcpOrgList.isEmpty()) {
                        binding.txtGcpProd.visibility = View.VISIBLE
                    }
                    prodGcpOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_GCP_LIST",Gson().toJson(prodGcpOrgList))
                    }
                    prodGcpOrgAdapter.setOrdNames(prodGcpOrgList)
                }
            }
        } else if(envType == "M"){
            lifecycleScope.launch {
                awsEastList = viewModel.getList("AWS_EAST_LIST") ?: ""
                if (awsEastList=="" || checkForDuplicates(awsEastList!!, orgName)) {
                    if (awsEastOrgList.isEmpty()) {
                        binding.txtAwsEast.visibility = View.VISIBLE
                    }
                    awsEastOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("AWS_EAST_LIST",Gson().toJson(awsEastOrgList))
                    }
                    awsEastOrgAdapter.setOrdNames(awsEastOrgList)
                }
            }
        } else if(envType == "C"){
            lifecycleScope.launch {
                prodGcpCanadaList = viewModel.getList("PROD_GCP_CANADA_LIST") ?: ""
                if (prodGcpCanadaList=="" || checkForDuplicates(prodGcpCanadaList!!, orgName)) {
                    if (prodGcpCanadaOrgList.isEmpty()) {
                        binding.txtGcpProdCanada.visibility = View.VISIBLE
                    }
                    prodGcpCanadaOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_GCP_CANADA_LIST",Gson().toJson(prodGcpCanadaOrgList))
                    }
                    prodGcpCanadaOrgAdapter.setOrdNames(prodGcpCanadaOrgList)
                }
            }
        } else if(envType == "F"){
            lifecycleScope.launch {
                prodAwsGovList = viewModel.getList("PROD_AWS_GOV_LIST") ?: ""
                if (prodAwsGovList=="" || checkForDuplicates(prodAwsGovList!!, orgName)) {
                    if (prodAwsGovOrgList.isEmpty()) {
                        binding.txtAwsGovProd.visibility = View.VISIBLE
                    }
                    prodAwsGovOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_AWS_GOV_LIST",Gson().toJson(prodAwsGovOrgList))
                    }
                    prodAwsGovOrgAdapter.setOrdNames(prodAwsGovOrgList)
                }
            }
        } else if(envType == "f"){
            lifecycleScope.launch {
                stagAwsGovList = viewModel.getList("STAG_AWS_GOV_LIST") ?: ""
                if (stagAwsGovList=="" || checkForDuplicates(stagAwsGovList!!, orgName)) {
                    if (stagAwsGovOrgList.isEmpty()) {
                        binding.txtAwsGovStag.visibility = View.VISIBLE
                    }
                    stagAwsGovOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("STAG_AWS_GOV_LIST",Gson().toJson(stagAwsGovOrgList))
                    }
                    stagAwsGovOrgAdapter.setOrdNames(stagAwsGovOrgList)
                }
            }
        } else if(envType == "A"){
            lifecycleScope.launch {
                prodAuList = viewModel.getList("PROD_AU_LIST") ?: ""
                if (prodAuList=="" || checkForDuplicates(prodAuList!!, orgName)) {
                    if (prodAuOrgList.isEmpty()) {
                        binding.txtAuProd.visibility = View.VISIBLE
                    }
                    prodAuOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_AU_LIST",Gson().toJson(prodAuOrgList))
                    }
                    prodAuOrgAdapter.setOrdNames(prodAuOrgList)
                }
            }
        } else if(envType == "c"){
            lifecycleScope.launch {
                canaryUsList = viewModel.getList("CANARY_US_LIST") ?: ""
                if (canaryUsList=="" || checkForDuplicates(canaryUsList!!, orgName)) {
                    if (canaryUSOrgList.isEmpty()) {
                        binding.txtCanaryUs.visibility = View.VISIBLE
                    }
                    canaryUSOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("CANARY_US_LIST",Gson().toJson(canaryUSOrgList))
                    }
                    canaryUsOrgAdapter.setOrdNames(canaryUSOrgList)
                }
            }
        } else if(envType == "U"){
            lifecycleScope.launch {
                prodGcpUkList = viewModel.getList("PROD_GCP_UK_LIST") ?: ""
                if (prodGcpUkList=="" || checkForDuplicates(prodGcpUkList!!, orgName)) {
                    if (prodGcpUkOrgList.isEmpty()) {
                        binding.txtUkGcpProd.visibility = View.VISIBLE
                    }
                    prodGcpUkOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_GCP_UK_LIST",Gson().toJson(prodGcpUkOrgList))
                    }
                    prodGcpUkOrgAdapter.setOrdNames(prodGcpUkOrgList)
                }
            }
        } else if(envType == "Y"){
            lifecycleScope.launch {
                prodAwsUaeList = viewModel.getList("PROD_AWS_UAE_LIST") ?: ""
                if (prodAwsUaeList=="" || checkForDuplicates(prodAwsUaeList!!, orgName)) {
                    if (prodAwsUaeOrgList.isEmpty()) {
                        binding.txtAwsUaeProd.visibility = View.VISIBLE
                    }
                    prodAwsUaeOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_AWS_UAE_LIST",Gson().toJson(prodAwsUaeOrgList))
                    }
                    prodAwsUaeOrgAdapter.setOrdNames(prodAwsUaeOrgList)
                }
            }
        } else if(envType == "W"){
            lifecycleScope.launch {
                prodGcpCentralList = viewModel.getList("PROD_GCP_CENTRAL_LIST") ?: ""
                if (prodGcpCentralList=="" || checkForDuplicates(prodGcpCentralList!!, orgName)) {
                    if (prodGcpCentralOrgList.isEmpty()) {
                        binding.txtGcpCentralProd.visibility = View.VISIBLE
                    }
                    prodGcpCentralOrgList.add(orgName)
                    lifecycleScope.launch {
                        viewModel.putList("PROD_GCP_CENTRAL_LIST",Gson().toJson(prodGcpCentralOrgList))
                    }
                    prodGcpCentralOrgAdapter.setOrdNames(prodGcpCentralOrgList)
                }
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