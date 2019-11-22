package com.hackday.subtysis

import com.hackday.subtysis.model.Keyword
import com.hackday.subtysis.model.SearchType
import com.hackday.subtysis.model.Subtitle
import java.io.File
import java.util.*

/**
 * @author Created by lee.cm on 2019-11-01.
 */
class Subtysis {
    lateinit var file: File
    lateinit var searchType: ArrayList<SearchType>
    lateinit var responseListener: SetResponseListener

    fun init(file: File, types: ArrayList<SearchType>) {
        this.file = file
        this.searchType = types
    }

    fun analyze() {
        val subtitleParser: SubtitleParser = SubtitleParserImpl()
        val subtitles: ArrayList<Subtitle> = subtitleParser.createSubtitle(file.path)

        val contentExtractor: ContentExtractor = ContentExtractorImpl()
        contentExtractor.initData(subtitles)
        val keywords: ArrayList<Keyword> = contentExtractor.getAllKeywords()

        val metadataCreator: MetadataCreator = MetadataCreatorImpl()
        metadataCreator.fillMetadata(keywords, searchType, responseListener)
    }

    fun setOnResponseListener(listener: SetResponseListener): Subtysis {
        responseListener = listener

        return this
    }
}