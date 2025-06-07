package com.lee.data.mapper

import com.lee.data.model.kakao.*
import com.lee.domain.model.kakao.*

object KakaoMapper {
    private fun mapperToDocuments(documentsDTO: DocumentsDTO) : Documents {
        val documents = documentsDTO.run {
            Documents(addressName,
                categoryGroupCode,
                categoryGroupName,
                categoryName,
                distance,
                id,
                phone,
                placeName,
                placeURL,
                roadAddressName,
                longitude,
                latitude)
        }
        return documents
    }

    private fun mapperToSameName(sameNameDTO: SameNameDTO) : SameName {
        val sameName = sameNameDTO.run {
            if(selectedRegion == null){
                SameName(keyword, region, null)
            } else {
                SameName(keyword , region , selectedRegion)
            }

        }
        return sameName
    }

    private fun mapperToMeta(metaDTO: MetaDTO) : Meta {
        val meta = metaDTO.run {
            Meta(isEnd , pageableCount , mapperToSameName(sameName) , totalCount)
        }
        return meta
    }


    fun mapperToKakaoPoi(kaKaoPoiDTO: KaKaoPoiDTO) : KaKaoPoi {
        val kaKaoPoi = kaKaoPoiDTO.run {
            val list = arrayListOf<Documents>()
            documents.forEach {
                val document = mapperToDocuments(it)
                list.add(document)
            }
            KaKaoPoi(list , mapperToMeta(meta))
        }
        return kaKaoPoi
    }

    private fun mapperToWcongDocuments(wcongDocumentsDTO: WcongDocumentsDTO) : WcongDocuments {
        val documents = wcongDocumentsDTO.run {
            WcongDocuments(longitude, latitude)
        }
        return documents
    }

    private fun mapperToWcongMeta(wcongMetaDTO: WcongMetaDTO) : WcongMeta {
        val wcongMeta = wcongMetaDTO.run {
            WcongMeta(totalCount)
        }
        return wcongMeta
    }

    fun mapperToWcong(wcongDTO: WcongDTO) : Wcong {
        val wcong = wcongDTO.run {
            val list = arrayListOf<WcongDocuments>()
            documents.forEach {
                val document = mapperToWcongDocuments(it)
                list.add(document)
            }
            Wcong(mapperToWcongMeta(meta) , list)
        }
        return wcong
    }
}