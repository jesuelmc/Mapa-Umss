package com.erwinlaura.agendafcyt.Admin


import android.app.Activity
import android.content.*
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.databinding.FragmentAdminBinding
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.Exception
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    companion object {
        const val REQUEST_CODE_GET_PDF = 25714

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)


        binding.buttonGetPdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, REQUEST_CODE_GET_PDF)
        }


        Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            "se ejecuto ",
            Snackbar.LENGTH_LONG
        ).show()



        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {

                when (requestCode) {
                    REQUEST_CODE_GET_PDF -> {
                        Snackbar.make(
                            activity!!.findViewById(android.R.id.content),
                            "reques exitoso ",
                            Snackbar.LENGTH_LONG
                        ).show()

                        loadPDF(data)

                    }
                }
            }

        }


    }

    private fun loadPDF(pdf: Intent) {

        val pdfSelected = pdf.data



        Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            "se ejecuto loadPdf ",
            Snackbar.LENGTH_LONG
        ).show()

        if (pdfSelected != null) {
       //     try {
                var parsededText = ""

                val fileInputStream= activity!!.contentResolver.openInputStream(pdfSelected)

                val reader: PdfReader = PdfReader(fileInputStream)
                PdfReader("")


                var pages = reader.numberOfPages
                lifecycle.coroutineScope.launch {

                    for (i in 0..pages) {
                        parsededText += PdfTextExtractor.getTextFromPage(
                            reader,
                            i + 1
                        ).trim() + "\n"
                    }
                    //aqui copiar al cortapapel

                    val clipboard: ClipboardManager =
                        activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData =
                        ClipData.newPlainText("se copio correctamente", parsededText)
                    clipboard.setPrimaryClip(clip)


                    reader.close()
                }
//            } catch (e: Exception) {
//                Snackbar.make(
//                    activity!!.findViewById(android.R.id.content),
//                    e.message.toString(),
//                    Snackbar.LENGTH_LONG
//                ).show()
//
//
//                val clipboard: ClipboardManager =
//                    activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                val clip: ClipData = ClipData.newPlainText("error", e.toString())
//                clipboard.setPrimaryClip(clip)
//            }

        }


    }


}
