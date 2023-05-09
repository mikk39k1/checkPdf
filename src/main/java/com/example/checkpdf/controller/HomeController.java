package com.example.checkpdf.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    @GetMapping("/error")
    public String error(){
        return "sites/filetype";
    }
    
    @GetMapping("/korrekt")
    public String korrektSide(){
        return "sites/korrekt";
    }

    @GetMapping("/fejl")
    public String fejlSide(){
        return "sites/fejl";
    }
    

    @PostMapping("/check-pdf")
    public String checkPdf(MultipartFile file){
        try{

            //Checking if file type is correct.
            if (!Objects.equals(file.getContentType(), "application/pdf")){
                return "sites/filetype";
            }

            // Reads the PDF and extracts a String if it is possible. If a String is detected, it will redirect to the
            // "Fejl" page, and throw out the PDF from the RAM Memory
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper textstripper = new PDFTextStripper();
            String text = textstripper.getText(document);
            document.close();
            if (text.trim().isEmpty()){
                return "redirect:/korrekt";
            } else {
                return "redirect:/fejl";
            }
        } catch (IOException | NullPointerException e){
            System.out.println(e);
        }

        return "redirect:/";
    }
}
