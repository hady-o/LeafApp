package com.example.leafapp

import android.content.res.AssetManager
import com.example.leafapp.dataclass.DiseaseClass
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader

object DiseasesData {
   public val lookUp: HashMap<String, DiseaseClass?> = hashMapOf("key" to null)
   fun loadData(assest: AssetManager){
       val inputStream = InputStreamReader(assest.open("Data/DiseasesDescription.csv"))
       val reader = BufferedReader(inputStream)
       val csvPars = CSVParser.parse(
           reader,
           CSVFormat.DEFAULT.withIgnoreHeaderCase().withRecordSeparator(',')
       )

       csvPars.forEach {
           it?.let {
               var tmp = DiseaseClass(
                   diseaseName = it[0],
                   symptoms = it[1],
                   cause = it[2],
                   howItStared = it[3],
                   tips = it[4]
               )
               lookUp[it[0]] = tmp
           }
       }
   }


}