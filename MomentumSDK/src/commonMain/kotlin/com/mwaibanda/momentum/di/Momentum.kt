package com.mwaibanda.momentum.di

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.parser.Parser
import com.mwaibanda.momentum.domain.usecase.message.MessageUseCases
import io.github.mwaibanda.authentication.di.Authentication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Momentum: KoinComponent {
    val messageUseCases: MessageUseCases by inject()
    val auth = Authentication
    fun tryXML() {
        val xu = Ksoup.parse("<data>\n" +
                "    <record>\n" +
                "        <name style='T'>'Test Record 1'</name>\n" +
                "        <description>'Test Record 1 Description'</description>\n" +
                "        <tag>critical</tag>\n" +
                "    </record>\n" +
                "    <record>\n" +
                "        <name>'Test Record 2'</name>\n" +
                "        <description>'Test Record 2 Description'</description>\n" +
                "        <tag>security</tag>\n" +
                "    </record>\n" +
                "</data>", Parser.xmlParser())
        println("::::::::::::::::::::::::::::::::::::::::::::::::::")
        xu.forEach {

            if(it.nodeName() == "record") {
                println(it.getElementsByTag("name").attr("style"))
            }

        }
        println("::::::::::::::::::::::::::::::::::::::::::::::::::")
    }
}