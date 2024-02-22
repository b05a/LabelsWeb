package com.example.labelsweb.Clases

class PageHtml {
    fun rotate(page:String):String{
        if (page.contains("rotate(90deg)")){
            return page.replace("rotate(90deg)", "rotate(270deg)")
        } else{
            return page.replace("rotate(270deg)", "rotate(90deg)")
        }
    }
    var mainPage = "<!DOCTYPE html>\n" +
            "<html style=\"height: 100%;\">\n" +
            "<head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, minimum-scale=0.1\">\n" +
            "    <style>\n" +
            "        .image-flex{\n" +
            "            display:flex;\n" +
            "            align-items:center;\n" +
            "            justify-content:center;\n" +
            "        }\n" +
            "        .image-wrap{\n" +
            "            width:100%;\n" +
            "            height:100%;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body style=\"height:100%; background-color:rgb(128, 128, 128)\">\n" +
            "<div class=\"image-flex image-wrap\">\n" +
            "    <img src=\"hello\"; style=\"transform: rotate(90deg)\"/>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>"
}