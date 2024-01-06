package com.callastro.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.databinding.ActivityUploadDocumentBinding
import com.callastro.viewModels.ConfirmationBookingsViewModel
import com.maxtra.astrorahiastrologer.util.CommonUtils
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

@AndroidEntryPoint
class UploadDocument : BaseActivity() {
    lateinit var binding: ActivityUploadDocumentBinding
    lateinit var id:String
    lateinit var user_id:String
    var pdfFile: MultipartBody.Part? = null
//    var pdfFile1: MultipartBody.Part? = null
    private val pickPdf = 9
    val FILE_BROWSER_CACHE_DIR = "doc"
    private val viewModel: ConfirmationBookingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_document)
        binding = DataBindingUtil.setContentView(this@UploadDocument,R.layout.activity_upload_document)
        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Report Request"

        if (intent != null){
            id = intent.getStringExtra("id").toString()
            user_id = intent.getStringExtra("user_id").toString()
        }

        binding.getPdf.setOnClickListener {
            selectPdf()
        }
        binding.btnSubmitReport.setOnClickListener {
            if (pdfFile == null){
                pdfFile =  MultipartBody.Part.createFormData("file","")
            }else{
                if (CommonUtils.isInternetAvailable(this)) {
                    viewModel.report_doc_upload(
                        "Bearer " +  userPref.getToken(),
                        user_id,
                        id,
                        binding.dicription.text.toString(),
                        pdfFile

                    )
                } else {
                    Log.d("TAG", "onCreate: " + "else part")
                    toast(this,"Please check internet connection.")
                }

            }

        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.commonResponse.observe(this){
            if (it.status == 1){
                toast(this@UploadDocument,"Response send successfully...")
                finish()
            }
        }

    }
    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "*/*"
            startActivityForResult(pdfIntent, pickPdf)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return
//        }
        if (requestCode == pickPdf) {
            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.filepath.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile = MultipartBody.Part.createFormData("file", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)
//            viewModel.LoaderdocAPI(
//                "Bearer "+userPref.getToken().toString(),
//                vehicleid,
//                "Rc-book",
//                pdfFile
//            )

        }


    }

    @Throws(IOException::class)
    private fun writeFileContent(uri: Uri): String? {
        val selectedFileInputStream = contentResolver.openInputStream(uri)
        if (selectedFileInputStream != null) {

            val mediaDir = externalMediaDirs.firstOrNull()?.let {
                File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
            }

            val certCacheDir = File(mediaDir, FILE_BROWSER_CACHE_DIR)
            var isCertCacheDirExists = certCacheDir.exists()
            if (!isCertCacheDirExists) {
                isCertCacheDirExists = certCacheDir.mkdirs()
            }
            if (isCertCacheDirExists) {
                var fileName:String?=getFileDisplayName(uri)
                fileName!!.replace("[^a-zA-Z0-9]", " ")
                val filePath = certCacheDir.absolutePath.toString() + "/" +fileName
                val selectedFileOutPutStream: OutputStream = FileOutputStream(filePath)
                val buffer = ByteArray(1024)
                var length: Int
                while (selectedFileInputStream.read(buffer).also { length = it } > 0) {
                    selectedFileOutPutStream.write(buffer, 0, length)
                }
                selectedFileOutPutStream.flush()
                selectedFileOutPutStream.close()
                return filePath
            }
            selectedFileInputStream.close()
        }
        return null
    }
    @SuppressLint("Range")
    @Nullable
    private fun getFileDisplayName(uri: Uri): String? {
        var displayName: String? = null
        contentResolver
            .query(uri, null, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        return displayName
    }
}