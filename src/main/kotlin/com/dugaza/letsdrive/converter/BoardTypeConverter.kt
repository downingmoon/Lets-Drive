package com.dugaza.letsdrive.converter

import com.dugaza.letsdrive.entity.community.BoardType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class BoardTypeConverter() : AttributeConverter<BoardType, String> {
    override fun convertToDatabaseColumn(p0: BoardType?): String {
        return p0!!.name
    }

    override fun convertToEntityAttribute(p0: String?): BoardType {
        return BoardType.valueOf(p0!!)
    }
}
